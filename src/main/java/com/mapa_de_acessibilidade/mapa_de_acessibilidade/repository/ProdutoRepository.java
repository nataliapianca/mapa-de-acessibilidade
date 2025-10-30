package com.mapa_de_acessibilidade.mapa_de_acessibilidade.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mapa_de_acessibilidade.mapa_de_acessibilidade.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}

// repositório Produto que estende JpaRepository para fornecer operações CRUD
// para a entidade Produto, esse arquivo e responsavel por indiretamente se
// comunicar com o banco de dados, ele que cria as queries a partir dos seus métodos.
