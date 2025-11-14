package com.mapa_de_acessibilidade.mapa_de_acessibilidade.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuario")

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Usuario extends Pessoa {


    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ComentarioUsuario> comentarios = new HashSet<>();

    // Getters e Setters
    public Set<ComentarioUsuario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(Set<ComentarioUsuario> comentarios) {
        this.comentarios = comentarios;
    }

   
    public Usuario(String nome, String email, String telefone, String login, Integer senha) {
        super(null, nome, email, telefone, login, senha);
        this.comentarios = new HashSet<>();
    }

    
    public void adicionarComentario(ComentarioUsuario comentario) {
        comentarios.add(comentario);
        comentario.setUsuario(this);
    }

    
    public void removerComentario(ComentarioUsuario comentario) {
        comentarios.remove(comentario);
        comentario.setUsuario(null);
    }

}
