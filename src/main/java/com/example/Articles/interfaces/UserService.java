package com.example.Articles.interfaces;

import com.example.Articles.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    List<User> getAllUsersSorted();
    Optional<User> findByUsername(String username);
    User createUser(User user);
    Optional<User> findById(Long id);
    User editUser(Long id, User updatedUser);
    void deleteUser(Long id);

}
