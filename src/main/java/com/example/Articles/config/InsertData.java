package com.example.Articles.config;
import com.example.Articles.entity.Article;
import com.example.Articles.repository.ArticleRepository;
import com.example.Articles.repository.TagRepository;
import com.example.Articles.repository.UserRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.beans.Transient;
import java.util.Random;



import com.example.Articles.entity.Tag;
import com.example.Articles.entity.User;
import java.util.*;



@Component
public class InsertData implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final TagRepository tagRepository;


    private final Faker faker = new Faker();
    private final Random random = new Random();

    public InsertData(UserRepository userRepository,
                      ArticleRepository articleRepository,
                      TagRepository tagRepository) {
         this.userRepository = userRepository;
        this.articleRepository = articleRepository;
        this.tagRepository = tagRepository;

    }

    @Transient
    @Override
    public void run(String... args) {
       createFakeUsers();
        createFakeTags();
        createFakeArticles();



    }

    private void createFakeUsers() {
        for (int i = 0; i < 5; i++) {
            User user = new User();
            System.out.println(faker.internet().emailAddress());
            user.setEmail(faker.internet().emailAddress());
            user.setUsername(faker.name().username());
            user.setImage_url(faker.internet().avatar());
            user.setPassword("password123");
            user.setBio(faker.lorem().sentence());
            userRepository.save(user);
        }
    }

    private void createFakeTags() {
        for (int i = 0; i < 5; i++) {
            Tag tag = new Tag();
            tag.setName(faker.lorem().word());
            tagRepository.save(tag);
        }
    }


    private void createFakeArticles() {
        List<User> users = userRepository.findAll();
        List<Tag> tags = tagRepository.findAll();

        if (users.isEmpty() || tags.isEmpty()) {
            return;
        }

        for (int i = 0; i < 10; i++) {
            Article article = new Article();
            article.setTitle(faker.book().title());
            article.setDescription(faker.lorem().sentence());
            article.setSlug(faker.lorem().word() + "-" + i);
            article.setContent(faker.lorem().paragraph());

            // Выбираем случайного пользователя из списка
            User randomUser = users.get(random.nextInt(users.size()));
            article.setUser(randomUser);

            // Присваиваем случайный набор тегов
            Set<Tag> randomTags = new HashSet<>();
            int numberOfTags = random.nextInt(tags.size()) + 1;
            for (int j = 0; j < numberOfTags; j++) {
                randomTags.add(tags.get(random.nextInt(tags.size())));
            }
            article.setTags(randomTags);

            articleRepository.save(article);
        }
    }

}