package com.example.Articles.repository;

import com.example.Articles.entity.ArticleFavorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleFavoriteRepository extends JpaRepository<ArticleFavorite, Long> {
    
}
