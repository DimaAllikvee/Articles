package com.example.Articles.interfaces;

import com.example.Articles.entity.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleService {
    List<Article>getAllArticle();
    List<Article>getAllArticlesSorted();
    Optional<Article> getArticleById(Long id);
    Article editArticle(Long id, Article article);
    Article createArticle(Article article);
    void deleteArticle(Long id);
    List<Article> getArticlesByUsername(String username);
    List<Article> getArticlesByTag(Long tagId);
    List<Article> searchArticles(String query);
}
