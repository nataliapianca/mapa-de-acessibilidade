package com.mapa_de_acessibilidade.mapa_de_acessibilidade.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "comentario_usuario")
public class ComentarioUsuario implements Serializable{

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String texto;
    private Integer nota;
    private String usuario;


    private LocalDateTime dataCriacao = LocalDateTime.now();


    @ManyToOne
    private Local local;
   
    @ManyToMany
    @JoinTable(
        name = "comentario_tag",
        joinColumns = @JoinColumn(name = "comentario_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<TagAcessibilidade> tagsComentadas = new HashSet<>();


    public ComentarioUsuario(){

    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getTexto() {
        return texto;
    }


    public void setTexto(String texto) {
        this.texto = texto;
    }


    public Integer getNota() {
        return nota;
    }


    public void setNota(Integer nota) {
        this.nota = nota;
    }


    public String getUsuario() {
        return usuario;
    }


    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }


    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }


    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }


    public Local getLocal() {
        return local;
    }


    public void setLocal(Local local) {
        this.local = local;
    }


    public Set<TagAcessibilidade> getTagsComentadas() {
        return tagsComentadas;
    }


    public void setTagsComentadas(Set<TagAcessibilidade> tagsComentadas) {
        this.tagsComentadas = tagsComentadas;
    }


}
