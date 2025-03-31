package com.example.Articles.ServiceImpl;

import com.example.Articles.entity.CustomUserDetails;
import com.example.Articles.entity.User;
import com.example.Articles.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        user.setPassword(user.getPassword().trim());
        System.out.println(user.getUsername() + " " + user.getEmail() + " " + user.getPassword());
        return CustomUserDetails.build(user);
    }
}
