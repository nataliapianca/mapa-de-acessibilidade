package com.mapa_de_acessibilidade.mapa_de_acessibilidade.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mapa_de_acessibilidade.mapa_de_acessibilidade.model.ComentarioUsuario;

public interface ComentarioRepository extends JpaRepository<ComentarioUsuario, Long>{
	
	//ele vai achar os comentarios de um local, ordenados por data de criação
	List<ComentarioUsuario> findByLocalIdOrderByDataCriacaoDesc(Long localId);
	
}
