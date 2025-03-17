package com.example.Articles.Service;

import com.example.Articles.Interface.ArticleService;
import com.example.Articles.entity.Article;
import com.example.Articles.entity.Author;
import com.example.Articles.entity.User;
import com.example.Articles.repository.ArticleRepository;
import com.example.Articles.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

        article.setAuthor(author);

        return articleRepository.save(article);
    }


    @Override
    public Article updateArticle(Long id, Article updatedArticle) {

        Article existingArticle = articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Статья не найдена с id=" + id));


        existingArticle.setTitle(updatedArticle.getTitle());
        existingArticle.setSlug(updatedArticle.getSlug());
        existingArticle.setDescription(updatedArticle.getDescription());
        existingArticle.setContent(updatedArticle.getContent());

        existingArticle.setUpdatedAt(LocalDateTime.now());


        return articleRepository.save(existingArticle);
    }


    @Override
    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }



    @Override
    public List<Article> getArticlesByTagId(Long tagId) {
        return articleRepository.findByTagsId(tagId);
    }

    @Override
    public List<Article> getArticleByTitle(String title) {
        return articleRepository.findByTitle(title);
    }


}
