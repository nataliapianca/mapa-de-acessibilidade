package com.mapa_de_acessibilidade.mapa_de_acessibilidade.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mapa_de_acessibilidade.mapa_de_acessibilidade.model.ComentarioUsuario;
import com.mapa_de_acessibilidade.mapa_de_acessibilidade.model.Icomentario;
import com.mapa_de_acessibilidade.mapa_de_acessibilidade.model.Local;
import com.mapa_de_acessibilidade.mapa_de_acessibilidade.model.TagAcessibilidade;
import com.mapa_de_acessibilidade.mapa_de_acessibilidade.repository.ComentarioRepository;
import com.mapa_de_acessibilidade.mapa_de_acessibilidade.repository.LocalRepository;
import com.mapa_de_acessibilidade.mapa_de_acessibilidade.repository.TagAcessibilidadeRepository;

@Service
public class ComentarioService implements Icomentario {

	private final ComentarioRepository comentarioRepository;
	private final LocalRepository localRepository;
	private final TagAcessibilidadeRepository tagRepository;
	private final LocalService localService;

	@Autowired
	public ComentarioService(ComentarioRepository comentarioRepository, LocalRepository localRepository,
			TagAcessibilidadeRepository tagRepository, LocalService localService) {
		super();
		this.comentarioRepository = comentarioRepository;
		this.localRepository = localRepository;
		this.tagRepository = tagRepository;
		this.localService = localService;
	}
	
	//crud ------------------------

	@Transactional    
	//create
	public ComentarioUsuario salvarComentario(Long localId, ComentarioUsuario comentario, Set<Long> tagIds) {
		Local local = localRepository.findById(localId).orElseThrow(() -> new RuntimeException("Local não encontrado."));

		if (!validarComentario(comentario)) {
			throw new RuntimeException("comentário inválido, verifique o texto e a nota (1 a 5).");
		}

		Set<TagAcessibilidade> tags = atribuirTags(tagIds);

		comentario.setTagsComentadas(tags);
		comentario.setLocal(local);

		ComentarioUsuario salvo = comentarioRepository.save(comentario);
		for (TagAcessibilidade tag : tags) {
			Double novoScore = calcularNovoScore(local.getId(), tag.getId(), comentario.getNota());
			localService.atualizarConfiancaTag(local.getId(), tag.getId(), novoScore);
		}

		return salvo;
	}

	//read
	public List<ComentarioUsuario> buscarPorLocal(Long localId) {
		return comentarioRepository.findByLocalIdOrderByDataCriacaoDesc(localId);
	}

	//delete
	public void deletarComentario(Long id) {
		if (!comentarioRepository.existsById(id)) {
			throw new RuntimeException("comentário não encontrado.");
		}
		comentarioRepository.deleteById(id);
	}

	//metodos da interface
	@Override
	public boolean validarComentario(ComentarioUsuario comentario) {
		if (comentario.getNota() == null || comentario.getNota() < 1 || comentario.getNota() > 5)
			return false;
		if (comentario.getDescricao() == null || comentario.getDescricao().isBlank())
			return false;
		return true;
	}

	@Override
	public Set<TagAcessibilidade> atribuirTags(Set<Long> tagIds) {
		return new HashSet<>(tagRepository.findAllById(tagIds));
	}


	private Double calcularNovoScore(Long localId, Long tagId, Integer novaNota) {
		List<ComentarioUsuario> comentarios = comentarioRepository.findByLocalIdOrderByDataCriacaoDesc(localId);
		int soma = 0;
		int contador = 0;
		for (ComentarioUsuario c : comentarios) {
			for (TagAcessibilidade tag : c.getTagsComentadas()) {
				if (tag.getId().equals(tagId)) {
					soma += c.getNota();
					contador++;
				}
			}
		}
		soma += novaNota;
		contador++;

		return (double) soma / contador;
	}
}
