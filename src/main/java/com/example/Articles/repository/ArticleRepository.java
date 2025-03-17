package com.example.Articles.repository;

import com.example.Articles.entity.Article;
import com.example.Articles.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;


import java.util.List;


@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findByAuthor(User author);
    List<Article> findByTitle(String title);
    List<Article> findByAuthorId(Long authorId);
    List<Article> findByTagsId(Long tagId);

}

