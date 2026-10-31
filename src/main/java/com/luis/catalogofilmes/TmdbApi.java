package com.luis.catalogofilmes;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class TmdbApi {

    static String buscarJson(String titulo) {
        try {
            String chave = Files.readString(Path.of("chave.txt")).trim();
            String url = "https://api.themoviedb.org/3/search/movie?api_key=" + chave
                    + "&query=" + URLEncoder.encode(titulo, StandardCharsets.UTF_8)
                    + "&language=pt-BR";
            HttpClient cliente = HttpClient.newHttpClient();
            HttpRequest pedido = HttpRequest.newBuilder().uri(URI.create(url)).build();
            HttpResponse<String> resposta = cliente.send(pedido, HttpResponse.BodyHandlers.ofString());
            return resposta.body();
        } catch (IOException | InterruptedException e) {
            return "Erro ao acessar a API.";
        }
    }

    static ArrayList<Filme> buscarFilmes(String titulo) {
        ArrayList<Filme> filmes = new ArrayList<>();
        try {
            String json = buscarJson(titulo);
            JsonObject raiz = JsonParser.parseString(json).getAsJsonObject();
            JsonArray resultados = raiz.getAsJsonArray("results");
            for (JsonElement elemento : resultados) {
                JsonObject item = elemento.getAsJsonObject();
                String nome = item.get("title").getAsString();
                double nota = item.get("vote_average").getAsDouble();
                JsonElement data = item.get("release_date");
                if (data == null || data.isJsonNull() || data.getAsString().length() < 4) {
                    continue;
                }
                int ano = Integer.parseInt(data.getAsString().substring(0, 4));
                filmes.add(new Filme(nome, ano, nota));
            }
        } catch (RuntimeException e) {
            System.out.println("Nao foi possivel ler a resposta da API.");
        }
        return filmes;
    }
}