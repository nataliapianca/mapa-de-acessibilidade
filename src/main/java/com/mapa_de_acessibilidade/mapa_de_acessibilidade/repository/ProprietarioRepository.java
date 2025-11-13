package com.mapa_de_acessibilidade.mapa_de_acessibilidade.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mapa_de_acessibilidade.mapa_de_acessibilidade.model.Proprietario;

/**
 * Repositório para a entidade Proprietario.
 * Fornece métodos de acesso aos dados de proprietários no banco.
 */
@Repository
public interface ProprietarioRepository extends JpaRepository<Proprietario, Long> {

    /**
     * Busca proprietários que possuem locais em uma determinada cidade.
     */
    @Query("SELECT DISTINCT p FROM Proprietario p JOIN p.locais l WHERE l.endereco LIKE %:cidade%")
    List<Proprietario> findProprietariosByCidade(@Param("cidade") String cidade);

    /**
     * Busca proprietários pelo nome (busca parcial, case insensitive).
     */
    List<Proprietario> findByNomeContainingIgnoreCase(String nome);

    /**
     * Conta quantos locais um proprietário possui.
     */
    @Query("SELECT COUNT(l) FROM Local l WHERE l.proprietario.id = :proprietarioId")
    Long countLocaisByProprietarioId(@Param("proprietarioId") Long proprietarioId);

}
