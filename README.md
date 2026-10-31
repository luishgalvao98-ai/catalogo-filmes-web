# Catálogo de Filmes Web

Versão web do [catálogo de filmes em Java](https://github.com/luishgalvao98-ai/catalogo-filmes-java), feita com Spring Boot.

## O que o site faz

- Busca filmes pelo título na API do TMDB
- Mostra os resultados em cartões, com título, ano e nota
- Permite favoritar e remover filmes
- Guarda os favoritos de cada visitante, separados por navegador (sessão)

## Tecnologias

- Java 25
- Spring Boot (Spring Web)
- Thymeleaf
- Maven
- Gson
- HTML e CSS
- API do TMDB
- IntelliJ IDEA, Git e GitHub

## Como rodar

1. Clone o repositório
2. Abra o projeto no IntelliJ IDEA
3. Crie uma chave gratuita da API em [themoviedb.org](https://www.themoviedb.org/) e salve em um arquivo `chave.txt` na raiz do projeto (o arquivo é ignorado pelo Git)
4. Execute a classe `CatalogoFilmesWebApplication`
5. Acesse http://localhost:8080

## Créditos

Dados fornecidos pela API do TMDB. Este produto usa a API do TMDB, mas não é endossado nem certificado pelo TMDB.

## Próximos passos

- [x] Busca de filmes na API do TMDB
- [x] Visual com cartões em CSS
- [x] Favoritos separados por visitante (sessão)
- [ ] Publicar o site online