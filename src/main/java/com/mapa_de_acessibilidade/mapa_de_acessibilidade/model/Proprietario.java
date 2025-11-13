package com.mapa_de_acessibilidade.mapa_de_acessibilidade.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe Proprietario que representa um proprietário de locais.
 * Herda de Pessoa e pode cadastrar vários locais.
 */
@Entity
@Table(name = "proprietario")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Proprietario extends Pessoa {

    /**
     * Relacionamento com Local: um proprietário pode cadastrar vários locais.
     */
    @OneToMany(mappedBy = "proprietario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Local> locais = new HashSet<>();

    /**
     * Construtor personalizado para facilitar a criação de proprietários.
     */
    public Proprietario(String nome, String email, String telefone, String login, Integer senha) {
        super(null, nome, email, telefone, login, senha);
        this.locais = new HashSet<>();
    }

    /**
     * Método auxiliar para adicionar um local ao proprietário.
     */
    public void adicionarLocal(Local local) {
        locais.add(local);
        local.setProprietario(this);
    }

    /**
     * Método auxiliar para remover um local do proprietário.
     */
    public void removerLocal(Local local) {
        locais.remove(local);
        local.setProprietario(null);
    }

}
