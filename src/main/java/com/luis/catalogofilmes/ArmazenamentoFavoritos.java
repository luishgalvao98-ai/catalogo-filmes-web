package com.luis.catalogofilmes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ArmazenamentoFavoritos {

    private static final Path ARQUIVO = Path.of("favoritos.txt");

    static void salvar(ArrayList<Filme> favoritos) {
        ArrayList<String> linhas = new ArrayList<>();
        for (Filme filme : favoritos) {
            linhas.add(filme.getTitulo() + ";" + filme.getAno() + ";" + filme.getNota());
        }
        try {
            Files.write(ARQUIVO, linhas);
        } catch (IOException e) {
            System.out.println("Nao foi possivel salvar os favoritos.");
        }
    }

    static ArrayList<Filme> carregar() {
        ArrayList<Filme> favoritos = new ArrayList<>();
        if (!Files.exists(ARQUIVO)) {
            return favoritos;
        }
        try {
            List<String> linhas = Files.readAllLines(ARQUIVO);
            for (String linha : linhas) {
                String[] partes = linha.split(";");
                if (partes.length < 3) {
                    continue;
                }
                Filme filme = new Filme(partes[0], Integer.parseInt(partes[1]), Double.parseDouble(partes[2]));
                filme.favorito = true;
                favoritos.add(filme);
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Nao foi possivel ler os favoritos.");
        }
        return favoritos;
    }
} 