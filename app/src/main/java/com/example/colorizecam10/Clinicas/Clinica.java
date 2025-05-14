package com.example.colorizecam10.Clinicas;

public class Clinica {
    private String nome;
    private String link;

    public Clinica(String nome, String link) {
        this.nome = nome;
        this.link = link;
    }

    public String getNome() {
        return nome;
    }

    public String getLink() {
        return link;
    }
}
