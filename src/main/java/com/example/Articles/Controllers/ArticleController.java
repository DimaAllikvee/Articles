package com.example.Articles.Controllers;

import com.example.Articles.Interface.ArticleService;
import com.example.Articles.Interface.AuthorService;
import com.example.Articles.entity.Article;
import com.example.Articles.entity.Author;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/articles/{id}")
    public String getArticleById(@PathVariable("id") Long id, Model model) {
        Optional<Article> optionalArticle = articleService.getArticleById(id);
        if (optionalArticle.isPresent()) {
            model.addAttribute("article", optionalArticle.get());
        } else {
            model.addAttribute("errorMessage", "Статья не найдена");
        }
        return "article/articles";
    }

    @GetMapping("/articles/searchById")
    public String searchById(@RequestParam("id") Long id) {
        return "redirect:/articles/" + id;
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
}
