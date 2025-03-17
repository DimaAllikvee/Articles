package com.example.Articles.repository;


import com.example.Articles.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, byte[]> {
    User findByUsername(String username);

    Optional<User> findById(Long id);

    void deleteById(Long id);

    //User findByEmail(String email);
}