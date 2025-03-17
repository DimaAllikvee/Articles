package com.example.Articles.Service;

import com.example.Articles.Interface.AuthorService;
import com.example.Articles.entity.Author;
import com.example.Articles.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorsServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorsServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }
}
