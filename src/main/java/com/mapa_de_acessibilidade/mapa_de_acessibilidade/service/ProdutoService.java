package com.mapa_de_acessibilidade.mapa_de_acessibilidade.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mapa_de_acessibilidade.mapa_de_acessibilidade.model.Produto;
import com.mapa_de_acessibilidade.mapa_de_acessibilidade.repository.ProdutoRepository;

// serviço Produto que contém a lógica de negócio para gerenciar produtos, é o coração
// da aplicação, ele faz a ponte entre o controller e o repositório.
@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    // método para salvar um novo produto
    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }

    // método para listar todos os produtos
    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    // método para buscar um produto por ID
    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }

    // método para atualizar um produto existente
    public Produto atualizar(Long id, Produto produtoAtualizado) {
        return produtoRepository.findById(id)
                .map(produto -> {
                    produto.setNome(produtoAtualizado.getNome());
                    produto.setPreco(produtoAtualizado.getPreco());
                    return produtoRepository.save(produto);
                }).orElse(null);
    }

    // método para deletar um produto por ID
    public boolean deletar(Long id) {
        if (produtoRepository.existsById(id)) {
            produtoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
