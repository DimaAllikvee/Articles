package com.example.Articles.Interface;

import com.example.Articles.entity.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleService {


    List<Article> getAllArticles();


    Optional<Article> getArticleById(Long id);


    Article createArticle(Article article, Long authorId);


    Article updateArticle(Long id, Article article);


    void deleteArticle(Long id);



    List<Article> getArticlesByTagId(Long tagId);

    List<Article> getArticleByTitle(String title);
}