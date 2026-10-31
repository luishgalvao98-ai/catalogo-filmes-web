package com.luis.catalogofilmes;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
public class FilmeController {

    @GetMapping("/")
    public String inicio(@RequestParam(required = false) String busca, Model model) {
        ArrayList<Filme> filmes = new ArrayList<>();
        if (busca != null && !busca.isBlank()) {
            filmes = TmdbApi.buscarFilmes(busca);
        } else {
            filmes.add(new Filme("Matrix", 1999, 8.7));
            filmes.add(new Filme("Interestelar", 2014, 8.6));
            filmes.add(new Filme("Parasita", 2019, 8.5));
        }
        model.addAttribute("filmes", filmes);
        model.addAttribute("titulo", "Catálogo de Filmes");
        model.addAttribute("modoFavoritos", false);
        return "filmes";
    }

    @PostMapping("/favoritar")
    public String favoritar(@RequestParam String titulo, @RequestParam int ano, @RequestParam double nota, HttpSession sessao) {
        ArrayList<Filme> favoritos = favoritosDa(sessao);
        for (Filme filme : favoritos) {
            if (filme.getTitulo().equals(titulo) && filme.getAno() == ano) {
                return "redirect:/favoritos";
            }
        }
        Filme novo = new Filme(titulo, ano, nota);
        novo.favorito = true;
        favoritos.add(novo);
        return "redirect:/favoritos";
    }

    @PostMapping("/remover")
    public String remover(@RequestParam String titulo, @RequestParam int ano, HttpSession sessao) {
        favoritosDa(sessao).removeIf(filme -> filme.getTitulo().equals(titulo) && filme.getAno() == ano);
        return "redirect:/favoritos";
    }

    @GetMapping("/favoritos")
    public String listarFavoritos(Model model, HttpSession sessao) {
        model.addAttribute("filmes", favoritosDa(sessao));
        model.addAttribute("titulo", "Meus favoritos");
        model.addAttribute("modoFavoritos", true);
        return "filmes";
    }

    @SuppressWarnings("unchecked")
    private ArrayList<Filme> favoritosDa(HttpSession sessao) {
        ArrayList<Filme> favoritos = (ArrayList<Filme>) sessao.getAttribute("favoritos");
        if (favoritos == null) {
            favoritos = new ArrayList<>();
            sessao.setAttribute("favoritos", favoritos);
        }
        return favoritos;
    }
}