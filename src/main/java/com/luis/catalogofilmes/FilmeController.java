package com.luis.catalogofilmes;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
public class FilmeController {
    private final ArrayList<Filme> favoritos = ArmazenamentoFavoritos.carregar();

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
    public String favoritar(@RequestParam String titulo, @RequestParam int ano, @RequestParam double nota) {
        for (Filme filme : favoritos) {
            if (filme.getTitulo().equals(titulo) && filme.getAno() == ano) {
                return "redirect:/favoritos";
            }
        }
        Filme novo = new Filme(titulo, ano, nota);
        novo.favorito = true;
        favoritos.add(novo);
        ArmazenamentoFavoritos.salvar(favoritos);
        return "redirect:/favoritos";
    }

    @PostMapping("/remover")
    public String remover(@RequestParam String titulo, @RequestParam int ano) {
        favoritos.removeIf(filme -> filme.getTitulo().equals(titulo) && filme.getAno() == ano);
        ArmazenamentoFavoritos.salvar(favoritos);
        return "redirect:/favoritos";
    }

    @GetMapping("/favoritos")
    public String listarFavoritos(Model model) {
        model.addAttribute("filmes", favoritos);
        model.addAttribute("titulo", "Meus favoritos");
        model.addAttribute("modoFavoritos", true);
        return "filmes";
    }
}