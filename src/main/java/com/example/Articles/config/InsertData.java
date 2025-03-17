package com.example.Articles.config;
import com.example.Articles.entity.Author;
import com.example.Articles.repository.ArticleRepository;
import com.example.Articles.repository.AuthorRepository;
import com.example.Articles.repository.TagRepository;
import com.example.Articles.repository.UserRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.beans.Transient;
import java.util.Random;


import com.example.Articles.entity.Article;
import com.example.Articles.entity.Tag;
import com.example.Articles.entity.User;
import java.time.LocalDateTime;
import java.util.*;



@Component
public class InsertData implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final TagRepository tagRepository;
    private final AuthorRepository authorRepository;

    private final Faker faker = new Faker();
    private final Random random = new Random();

    public InsertData(UserRepository userRepository,
                      ArticleRepository articleRepository,
                      TagRepository tagRepository, AuthorRepository authorRepository) {
         this.userRepository = userRepository;
        this.articleRepository = articleRepository;
        this.tagRepository = tagRepository;
        this.authorRepository = authorRepository;
    }

    @Transient
    @Override
    public void run(String... args) {

         //List<User> savedUsers = createFakeUsers(5);
         //List<Tag> savedTags = createFakeTags(5);
         //createFakeArticles(10, savedUsers, savedTags);
         //List<Author> savedAuthors = createFakeAuthors(10);


    }

    private List<User> createFakeUsers(int count) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            User user = new User();
            user.setUsername(faker.name().username());
            user.setEmail(faker.internet().emailAddress());
            user.setPassword(faker.internet().password());
            user.setBio(faker.lorem().sentence(5));
            user.setImageUrl(faker.internet().avatar());

            userRepository.save(user);
            users.add(user);
        }
        return users;
    }

    private List<Tag> createFakeTags(int count) {
        List<Tag> tags = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Tag tag = new Tag();
            tag.setName(faker.lorem().word());
            tag.setCreatedAt(LocalDateTime.now());
            tagRepository.save(tag);
            tags.add(tag);
        }
        return tags;
    }


    private List<Author> createFakeAuthors(int count) {
        List<Author> authors = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Author author = new Author();
            author.setFirstName(faker.name().firstName());
            author.setLastName(faker.name().lastName());

            authorRepository.save(author);
            authors.add(author);
        }
        return authors;
    }

    private void createFakeArticles(int count, List<Author> authors, List<Tag> tags) {
        for (int i = 0; i < count; i++) {
            Article article = new Article();


            Author randomAuthor = authors.get(random.nextInt(authors.size()));

            article.setAuthor(randomAuthor);


            String title = faker.book().title();
            article.setTitle(title);
            String slug = title.toLowerCase().replaceAll("[^a-z0-9]+", "-");
            article.setSlug(slug);


            article.setDescription(faker.lorem().sentence(1));
            article.setContent(faker.lorem().paragraph(3));


            articleRepository.save(article);


            Set<Tag> randomTags = new HashSet<>();
            int tagsCount = random.nextInt(tags.size()) + 1;
            for (int t = 0; t < tagsCount; t++) {
                Tag randomTag = tags.get(random.nextInt(tags.size()));
                randomTags.add(randomTag);
            }


            article.setTags(randomTags);


            articleRepository.save(article);
        }
    }

}
