package com.example.Articles.ServiceImpl;

import com.example.Articles.entity.Article;
import com.example.Articles.interfaces.ArticleService;
import com.example.Articles.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;


    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public List<Article> getAllArticle() {
      return articleRepository.findAll();
    }

    @Override
    public List<Article> getAllArticlesSorted() {
        return articleRepository.findAllByOrderByCreatedAtDesc();
    }

    @Override
    public Optional<Article> getArticleById(Long id) {
        return articleRepository.findById(id);
    }

    @Override
    public Article editArticle(Long id, Article updatedArticle) {
        return articleRepository.findById(id)
                .map(article -> {
                    article.setTitle(updatedArticle.getTitle());
                    article.setDescription(updatedArticle.getDescription());
                    article.setSlug(updatedArticle.getSlug());
                    article.setContent(updatedArticle.getContent());
                    article.setTags(updatedArticle.getTags());
                    return articleRepository.save(article);
                })
                .orElseThrow(() -> new IllegalArgumentException("Статья не найдена"));
    }

    @Override
    public Article createArticle(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }

    @Override
    public List<Article> getArticlesByUsername(String username) {
        return articleRepository.findByUser_Username(username);
    }

    @Override
    public List<Article> getArticlesByTag(Long tagId) {
        return articleRepository.findByTags_Id(tagId);
    }

    @Override
    public List<Article> searchArticles(String query) {

        return articleRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(query, query);
    }



}
