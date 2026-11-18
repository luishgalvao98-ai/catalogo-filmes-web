package com.luis.catalogofilmes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GeminiApi {

    private static final String MODELO = "gemini-3.5-flash-lite";

    static String lerChave() throws IOException {
        String chave = System.getenv("GEMINI_API_KEY");
        if (chave == null || chave.isBlank()) {
            chave = Files.readString(Path.of("gemini.txt")).trim();
        }
        return chave;
    }

    static String montarCorpo(String pergunta) {
        JsonObject parte = new JsonObject();
        parte.addProperty("text", pergunta);
        JsonArray partes = new JsonArray();
        partes.add(parte);
        JsonObject conteudo = new JsonObject();
        conteudo.add("parts", partes);
        JsonArray conteudos = new JsonArray();
        conteudos.add(conteudo);
        JsonObject corpo = new JsonObject();
        corpo.add("contents", conteudos);
        return corpo.toString();
    }

    static String perguntar(String pergunta) {
        try {
            HttpRequest pedido = HttpRequest.newBuilder()
                    .uri(URI.create("https://generativelanguage.googleapis.com/v1beta/models/" + MODELO + ":generateContent"))
                    .header("Content-Type", "application/json")
                    .header("x-goog-api-key", lerChave())
                    .POST(HttpRequest.BodyPublishers.ofString(montarCorpo(pergunta)))
                    .build();
            HttpResponse<String> resposta = HttpClient.newHttpClient()
                    .send(pedido, HttpResponse.BodyHandlers.ofString());
            if (resposta.statusCode() != 200) {
                return "A IA respondeu com erro (código " + resposta.statusCode() + ").";
            }
            return JsonParser.parseString(resposta.body()).getAsJsonObject()
                    .getAsJsonArray("candidates").get(0).getAsJsonObject()
                    .getAsJsonObject("content").getAsJsonArray("parts").get(0).getAsJsonObject()
                    .get("text").getAsString();
        } catch (IOException | InterruptedException | RuntimeException e) {
            return "Não foi possível falar com a IA agora.";
        }
    }
}