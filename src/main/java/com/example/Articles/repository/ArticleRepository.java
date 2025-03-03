package com.example.Articles.repository;

import com.example.Articles.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;


@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    //List<Article> findByAuthor(String author);

    //List<Article> findByTitle(String title);







}
