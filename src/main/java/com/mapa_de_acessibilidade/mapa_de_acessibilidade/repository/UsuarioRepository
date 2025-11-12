package com.mapa_de_acessibilidade.mapa_de_acessibilidade.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mapa_de_acessibilidade.mapa_de_acessibilidade.model.Usuario;

/**
 * Repositório para a entidade Usuario.
 * Fornece métodos de acesso aos dados de usuários no banco.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca usuários que fizeram comentários em um local específico.
     */
    @Query("SELECT DISTINCT u FROM Usuario u JOIN u.comentarios c WHERE c.local.id = :localId")
    List<Usuario> findUsuariosByLocalId(@Param("localId") Long localId);

    /**
     * Busca usuários pelo nome (busca parcial, case insensitive).
     */
    List<Usuario> findByNomeContainingIgnoreCase(String nome);

}
