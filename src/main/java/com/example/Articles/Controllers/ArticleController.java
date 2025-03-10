package com.example.Articles.Controllers;

import com.example.Articles.Interface.ArticleService;
import com.example.Articles.entity.Article;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Optional;

@Controller
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/articles")
    public String getAllArticles(Model model) {
       model.addAttribute("articles", articleService.getAllArticles());
        return "article/articles";
    }

    @GetMapping("/articles/{id}")
    public String getArticleById(@PathVariable("id") Long id, Model model) {
        Optional<Article> article = articleService.getArticleById(id);
        model.addAttribute("articles", article);
        return "article/articles";
    }
}
