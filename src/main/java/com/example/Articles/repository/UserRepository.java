package com.example.Articles.repository;

import com.example.Articles.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    List<User> findAll();
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);


}
