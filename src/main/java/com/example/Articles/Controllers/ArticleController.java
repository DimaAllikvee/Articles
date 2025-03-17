package com.example.Articles.Controllers;

import com.example.Articles.Interface.ArticleService;
import com.example.Articles.Interface.AuthorService;
import com.example.Articles.entity.Article;
import com.example.Articles.entity.Author;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ArticleController {

    private final ArticleService articleService;
    private final AuthorService authorService;

    public ArticleController(ArticleService articleService, AuthorService authorService) {
        this.articleService = articleService;

        this.authorService = authorService;
    }

    @GetMapping("/articles")
    public String getAllArticles(Model model) {
       model.addAttribute("articles", articleService.getAllArticles());
        return "article/articles";
    }




    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("article", new Article());

        List<Author> authorsList = authorService.getAllAuthors();
        model.addAttribute("authors", authorsList);

        return "article/createArticle";
    }


    @PostMapping("/create")
    public String createArticle(
            @ModelAttribute Article article,
            @RequestParam("authorId") Long authorId
    ) {

        articleService.createArticle(article, authorId);

        return "redirect:/articles";
    }

    @GetMapping("/articles/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Article currentArticle = articleService.getArticleById(id)
                .orElseThrow(() -> new RuntimeException("Статья не найдена! id=" + id));

        model.addAttribute("article", currentArticle);
        return "article/editArticle";
    }

    @PostMapping("/articles/edit/{id}")
    public String updateArticle(@PathVariable Long id,
                                @ModelAttribute Article article) {
        articleService.updateArticle(id, article);
        return "redirect:/articles";
    }


    @GetMapping("/articles/delete/{id}")
    public String deleteArticle(@PathVariable("id") Long id) {
        articleService.deleteArticle(id);
        return "redirect:/articles";
    }

    @GetMapping("/articles/multiSearch")
    public String multiSearch(
            @RequestParam("searchType") String searchType,
            @RequestParam("searchValue") String searchValue,
            Model model
    ) {
        List<Article> results = new ArrayList<>();

        switch (searchType) {
            case "articleId": {
                // Поиск статьи по ID
                try {
                    Long articleId = Long.valueOf(searchValue);
                    Optional<Article> maybeArticle = articleService.getArticleById(articleId);
                    maybeArticle.ifPresent(results::add);
                } catch (NumberFormatException e) {
                    model.addAttribute("errorMessage", "ID статьи должен быть числом.");
                }
                break;
            }
            case "tagId": {
                // Поиск статей по тегу
                try {
                    Long tagId = Long.valueOf(searchValue);
                    List<Article> articlesByTag = articleService.getArticlesByTagId(tagId);
                    results.addAll(articlesByTag);
                } catch (NumberFormatException e) {
                    model.addAttribute("errorMessage", "ID тега должен быть числом.");
                }
                break;
            }
            case "articleTitle": {
                // Поиск статьи по названию
                List<Article> articlesByTitle = articleService.getArticleByTitle(searchValue);
                results.addAll(articlesByTitle);
                break;
            }
            default:
                model.addAttribute("errorMessage", "Неизвестный тип поиска");
        }

        model.addAttribute("articles", results);
        return "article/articles";
    }
}




