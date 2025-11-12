package com.mapa_de_acessibilidade.mapa_de_acessibilidade.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mapa_de_acessibilidade.mapa_de_acessibilidade.model.Pessoa;

/**
 * Repositório para a entidade Pessoa.
 * Fornece métodos de acesso aos dados de pessoas no banco.
 */
@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    /**
     * Busca uma pessoa pelo email.
     */
    Optional<Pessoa> findByEmail(String email);

    /**
     * Busca uma pessoa pelo login.
     */
    Optional<Pessoa> findByLogin(String login);

    /**
     * Verifica se existe uma pessoa com o email informado.
     */
    boolean existsByEmail(String email);

    /**
     * Verifica se existe uma pessoa com o login informado.
     */
    boolean existsByLogin(String login);

}
