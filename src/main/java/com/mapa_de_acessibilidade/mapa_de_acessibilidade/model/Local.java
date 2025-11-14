package com.mapa_de_acessibilidade.mapa_de_acessibilidade.model;

import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "local")
public class Local {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Chave primária

    @Column(nullable = false)
    private String nome; // Nome do local

    @Column(nullable = false, columnDefinition = "TEXT") 
    private String descricao;  // Recomendado usar TEXT para que o MySQL possa armazenar descrições longas

    @Column(nullable = false)
    private String endereco; // Endereço completo

    private double latitude; // Coordenadas para exibição
    private double longitude;

    // NOVO RELACIONAMENTO
    // O Local agora tem um conjunto de associações LocalTag
    @OneToMany(mappedBy = "local", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<LocalTag> tagsComScore; // Renomeado para maior clareza


    public Local() {}


    public Local(String nome, String descricao, String endereco, double latitude, double longitude,
            Set<LocalTag> tagsComScore) {
        this.nome = nome;
        this.descricao = descricao;
        this.endereco = endereco;
        this.latitude = latitude;
        this.longitude = longitude;
        this.tagsComScore = tagsComScore;
    }


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEndereco() {
        return endereco;
    }
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


    public Set<LocalTag> getTagsComScore() {
        return tagsComScore;
    }
    public void setTagsComScore(Set<LocalTag> tagsComScore) {
        this.tagsComScore = tagsComScore;
    }

   

}