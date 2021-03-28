package com.kenuiworks.frameworkbox.model;

import javax.persistence.*;

@Entity
public class Framework {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String descricao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "Framework{" +
                "titulo='" + titulo + '\'' +
                ", descricao='" + getBreveDescricao() + '\'' +
                '}';
    }

    private String getBreveDescricao() {
        return descricao;
    }
}
