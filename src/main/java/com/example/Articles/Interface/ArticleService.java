package com.example.Articles.Interface;

import com.example.Articles.entity.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleService {

    // Получить список всех статей
    List<Article> getAllArticles();

    // Получить статью по ID
    Optional<Article> getArticleById(Long id);

    // Добавить новую статью
    Article createArticle(Article article);

    // Обновить статью
    Article updateArticle(Long id, Article article);

    // Удалить статью
    void deleteArticle(Long id);

    // Получить статьи по автору
    List<Article> getArticlesByAuthorId(Long authorId);

    // Получить статьи по тегу
    List<Article> getArticlesByTagId(Long tagId);

    // Поиск статей по заголовку или содержимому
    List<Article> searchArticles(String query);
}