package com.mapa_de_acessibilidade.mapa_de_acessibilidade.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mapa_de_acessibilidade.mapa_de_acessibilidade.model.Proprietario;
import com.mapa_de_acessibilidade.mapa_de_acessibilidade.repository.ProprietarioRepository;


@Service
public class ProprietarioService {

    private final ProprietarioRepository proprietarioRepository;
    private final PessoaService pessoaService; // Reutiliza a lógica de validação de Pessoa

    @Autowired
    public ProprietarioService(ProprietarioRepository proprietarioRepository, PessoaService pessoaService) {
        this.proprietarioRepository = proprietarioRepository;
        this.pessoaService = pessoaService;
    }

    
    public List<Proprietario> buscarTodos() {
        return proprietarioRepository.findAll();
    }

    
    public Optional<Proprietario> buscarPorId(Long id) {
        return proprietarioRepository.findById(id);
    }

    
    @Transactional
    public Proprietario salvarProprietario(Proprietario proprietario) {
        // A validação de email/login único é feita no PessoaService
        return (Proprietario) pessoaService.salvarPessoa(proprietario);
    }

    
    @Transactional
    public Proprietario atualizarProprietario(Long id, Proprietario detalhesProprietario) {
        // A validação de email/login único é feita no PessoaService
        return (Proprietario) pessoaService.atualizarPessoa(id, detalhesProprietario);
    }

    
    public void deletarProprietario(Long id) {
        // Verifica se o proprietário possui locais antes de deletar
        Long countLocais = proprietarioRepository.countLocaisByProprietarioId(id);
        if (countLocais > 0) {
            throw new RuntimeException("Proprietário não pode ser deletado pois possui " + countLocais + " locais cadastrados.");
        }
        pessoaService.deletarPessoa(id);
    }

    
    public List<Proprietario> buscarProprietariosPorCidade(String cidade) {
        return proprietarioRepository.findProprietariosByCidade(cidade);
    }

    public List<Proprietario> buscarPorNome(String nome) {
        return proprietarioRepository.findByNomeContainingIgnoreCase(nome);
    }

    
    public Long contarLocais(Long proprietarioId) {
        return proprietarioRepository.countLocaisByProprietarioId(proprietarioId);
    }
}
