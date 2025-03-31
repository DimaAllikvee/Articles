package com.example.Articles.ServiceImpl;

import com.example.Articles.interfaces.AuthService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public String getCurrentUsername(Authentication authentication) {
        return (authentication != null && authentication.isAuthenticated())
                ? authentication.getName()
                : null;
    }

    @Override
    public boolean isAdmin(Authentication authentication) {
        return authentication != null &&
                authentication.getAuthorities().stream()
                        .anyMatch(auth -> "ADMIN".equals(auth.getAuthority()));
    }
}
