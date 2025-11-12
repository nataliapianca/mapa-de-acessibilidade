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
 * Classe Usuario que representa um usuário do sistema,
 * herda de Pessoa e pode fazer comentários sobre locais.
 */
@Entity
@Table(name = "usuario")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Usuario extends Pessoa {

    /**
     * Relacionamento com Comentario: um usuário pode fazer vários comentários.
     */
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ComentarioUsuario> comentarios = new HashSet<>();

    /**
     * Construtor personalizado para facilitar a criação de usuários.
     */
    public Usuario(String nome, String email, String telefone, String login, Integer senha) {
        super(null, nome, email, telefone, login, senha);
        this.comentarios = new HashSet<>();
    }

    /**
     * Método auxiliar para adicionar um comentário ao usuário.
     */
    public void adicionarComentario(ComentarioUsuario comentario) {
        comentarios.add(comentario);
        comentario.setUsuario(this);
    }

    /**
     * Método auxiliar para remover um comentário do usuário.
     */
    public void removerComentario(ComentarioUsuario comentario) {
        comentarios.remove(comentario);
        comentario.setUsuario(null);
    }

}
