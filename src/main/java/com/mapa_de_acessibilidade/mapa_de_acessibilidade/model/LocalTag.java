package com.mapa_de_acessibilidade.mapa_de_acessibilidade.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "local_tag")
public class LocalTag implements Serializable{

    // Chave Composta: a combinação Local + Tag é única
    @EmbeddedId
    private LocalTagId id; // Usaremos uma classe auxiliar para a chave primária

    // Relacionamento com Local
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("localId") // Mapeia para o atributo localId dentro de LocalTagId
    private Local local;

    // Relacionamento com TagAcessibilidade
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("tagId") // Mapeia para o atributo tagId dentro de LocalTagId
    private TagAcessibilidade tag;

    // NOVO ATRIBUTO DE CONFIANÇA
    // Será atualizado pelo Service de Comentário (BE4), 
    // mas persistido por você (BE1)
    @Column(name = "confianca_score", nullable = false)
    private Double confiancaScore = 0.0; // Inicia com 100% de confiança (Primeira inserção)


    // Construtores, Getters e Setters (devem ser implementados)
    public LocalTag() {}

    public LocalTag(Local local, TagAcessibilidade tag) {
        this.local = local;
        this.tag = tag;
        this.id = new LocalTagId(local.getId(), tag.getId());
        this.confiancaScore = 0.0;
    }

   
    public LocalTagId getId() {
        return this.id;
    }
    public void setId(LocalTagId id) {
        this.id = id;
    }

    public Local getLocal() {
        return local;
    }
    public void setLocal(Local local) {
        this.local = local;
    }

    public TagAcessibilidade getTag() {
        return tag;
    }
    public void setTag(TagAcessibilidade tag) {
        this.tag = tag;
    }

    public Double getConfiancaScore() {
        return confiancaScore;
    }
    public void setConfiancaScore(Double confiancaScore) {
        this.confiancaScore = confiancaScore;
    }


}


// Classe Auxiliar para a Chave Composta
@Embeddable
class LocalTagId implements Serializable {
    @Column(name = "local_id")
    private Long localId;

    @Column(name = "tag_id")
    private Long tagId;


     // Construtores, equals() e hashCode() são obrigatórios
    public LocalTagId(Long localId, Long tagId) {
        this.localId = localId;
        this.tagId = tagId;
    }

    public LocalTagId() {}


   @Override
    public int hashCode() {
        return Objects.hash(localId, tagId);
    } 


   @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocalTagId that = (LocalTagId) o;
        return Objects.equals(localId, that.localId) &&
               Objects.equals(tagId, that.tagId);
    }


    public Long getLocalId() {
        return localId;
    }
    public void setLocalId(Long localId) {
        this.localId = localId;
    }

    public Long getTagId() {
        return tagId;
    }
    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }


    
}