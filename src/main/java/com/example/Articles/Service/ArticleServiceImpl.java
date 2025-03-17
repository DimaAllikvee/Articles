package com.example.Articles.Service;

import com.example.Articles.Interface.ArticleService;
import com.example.Articles.entity.Article;
import com.example.Articles.entity.Author;
import com.example.Articles.entity.User;
import com.example.Articles.repository.ArticleRepository;
import com.example.Articles.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final AuthorRepository authorRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository, AuthorRepository authorRepository) {
        this.articleRepository = articleRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Article> getAllArticles() {
        List<Article> articles = articleRepository.findAll();
        System.out.println("Загружено статей: " + articles.size());
        return articles;
    }


    @Override
    public Optional<Article> getArticleById(Long id) {
        return articleRepository.findById(id);
    }

    @Override
    public Article createArticle(Article article, Long authorId) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("Author not found with id=" + authorId));

        // Устанавливаем автора для статьи:
        article.setAuthor(author);

        // И только после этого сохраняем
        return articleRepository.save(article);
    }


    @Override
    public Article updateArticle(Long id, Article article) {
        return null;
    }

    @Override
    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }

    @Override
    public List<Article> getArticlesByAuthorId(Long authorId) {
        return null;
    }

    @Override
    public List<Article> getArticlesByTagId(Long tagId) {
        return null;
    }

    @Override
    public List<Article> searchArticles(String Article) {
        return articleRepository.findByTitle(Article);
    }
}
