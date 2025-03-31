package com.example.Articles.interfaces;

import org.springframework.security.core.Authentication;

public interface AuthService {

    String getCurrentUsername(Authentication authentication);

    boolean isAdmin(Authentication authentication);
}
