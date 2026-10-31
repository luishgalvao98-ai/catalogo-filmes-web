package com.luis.catalogofilmes;

public class Filme {
    String titulo;
    int ano;
    double nota;
    boolean favorito;

    public Filme(String titulo, int ano, double nota) {
        this.titulo = titulo;
        this.ano = ano;
        this.nota = nota;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getAno() {
        return ano;
    }

    public double getNota() {
        return nota;
    }

    public boolean isFavorito() {
        return favorito;
    }
}
