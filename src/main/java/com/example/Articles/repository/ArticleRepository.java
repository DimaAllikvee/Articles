package com.example.Articles.repository;

import com.example.Articles.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findAllByOrderByCreatedAtDesc();
    List<Article> findByUser_Username(String username);
    List<Article> findByTags_Id(Long tagId);
    List<Article> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String title, String content);
    List<Article> findByUserId(Long userId);
}
