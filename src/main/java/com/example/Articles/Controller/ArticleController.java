package com.example.Articles.Controller;
import com.example.Articles.ServiceImpl.AuthServiceImpl;
import com.example.Articles.ServiceImpl.TagServiceImpl;
import com.example.Articles.entity.Article;
import com.example.Articles.entity.User;
import com.example.Articles.interfaces.ArticleService;
import com.example.Articles.interfaces.TagService;
import com.example.Articles.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;
    private final AuthServiceImpl authServiceImpl;
    private final TagService tagService;
    private final UserService userService;

    @Autowired
    public ArticleController(ArticleService articleService, AuthServiceImpl authServiceImpl, TagService tagService, UserService userService) {
        this.articleService = articleService;
        this.authServiceImpl = authServiceImpl;
        this.tagService = tagService;

        this.userService = userService;
    }

    @GetMapping
    public String getAllArticles(Model model, Authentication authentication) {
        model.addAttribute("articles", articleService.getAllArticlesSorted());
        model.addAttribute("currentUsername", authServiceImpl.getCurrentUsername(authentication));
        model.addAttribute("isAdmin", authServiceImpl.isAdmin(authentication));
        return "articles/list";
    }

    @GetMapping("/{id}")
    public String getArticleById(@PathVariable Long id, Model model, Authentication authentication) {
        Article article = articleService.getArticleById(id)
                .orElseThrow(() -> new IllegalArgumentException("Article not found"));
        model.addAttribute("article", article);
        model.addAttribute("currentUsername", authServiceImpl.getCurrentUsername(authentication));
        model.addAttribute("isAdmin", authServiceImpl.isAdmin(authentication));
        return "articles/details";
    }


    @PostMapping("/edit/{id}")
    public String editArticle(@PathVariable Long id, Article updatedArticle, Authentication authentication) {
        // получаем статью из базы
        Article article = articleService.getArticleById(id).orElseThrow();

        String username = authentication.getName();
        boolean isAdmin = authServiceImpl.isAdmin(authentication);

        // Если не админ и не автор — нельзя редактировать
        if (!isAdmin && !article.getUser().getUsername().equals(username)) {
            return "redirect:/articles?error=not-authorized";
        }



        article.setTitle(updatedArticle.getTitle());
        article.setDescription(updatedArticle.getDescription());
        article.setContent(updatedArticle.getContent());
        article.setSlug(updatedArticle.getSlug());
        article.setTags(updatedArticle.getTags());


        articleService.editArticle(article.getId(), article);
        return "redirect:/articles";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, Authentication authentication) {
        Article article = articleService.getArticleById(id).orElseThrow();

        // AuthService для получения имени пользователя и проверки роли
        String username = authServiceImpl.getCurrentUsername(authentication);
        boolean isAdmin = authServiceImpl.isAdmin(authentication);

        // Если пользователь не админ и не автор статьи, перенаправляем с ошибкой
        if (!isAdmin && !article.getUser().getUsername().equals(username)) {
            return "redirect:/articles?error=not-authorized";
        }

        model.addAttribute("article", article);
        model.addAttribute("tags", tagService.getAllTags());
        return "articles/edit";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("article", new Article());
        model.addAttribute("tags", tagService.getAllTags());
        return "articles/create";
    }

    @PostMapping
    public String createArticle(@ModelAttribute Article article, Authentication authentication) {
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        User currentUser = userService.findByUsername(username).orElseThrow();
        article.setUser(currentUser);
        articleService.createArticle(article);
        return "redirect:/articles";
    }

    @GetMapping("/delete/{id}")
    public String deleteArticle(@PathVariable Long id, Authentication authentication) {
        Article article = articleService.getArticleById(id).orElseThrow(() -> new IllegalArgumentException("Статья не найдена"));
        String username = authentication.getName();


        boolean isAdmin = authServiceImpl.isAdmin(authentication);

        // удаление только если является администратором или автором статьи
        if (!isAdmin && !article.getUser().getUsername().equals(username)) {
            return "redirect:/articles?error=not-authorized";
        }

        articleService.deleteArticle(id);
        return "redirect:/articles";
    }


    @GetMapping("/by-author/{username}")
    public String getArticlesByAuthor(@PathVariable String username, Model model, Authentication authentication) {
        List<Article> articles = articleService.getArticlesByUsername(username);
        model.addAttribute("articles", articles);
        model.addAttribute("currentUsername", authServiceImpl.getCurrentUsername(authentication));
        model.addAttribute("isAdmin", authServiceImpl.isAdmin(authentication));
        return "articles/list";
    }

    @GetMapping("/by-tag/{tagId}")
    public String getArticlesByTag(@PathVariable Long tagId, Model model, Authentication authentication) {
        List<Article> articles = articleService.getArticlesByTag(tagId);
        model.addAttribute("articles", articles);
        model.addAttribute("currentUsername", authServiceImpl.getCurrentUsername(authentication));
        model.addAttribute("isAdmin", authServiceImpl.isAdmin(authentication));
        return "articles/list";
    }

    @GetMapping("/search")
    public String searchArticles(@RequestParam("q") String query, Model model, Authentication authentication) {
        List<Article> articles = articleService.searchArticles(query);
        model.addAttribute("articles", articles);
        model.addAttribute("currentUsername", authServiceImpl.getCurrentUsername(authentication));
        model.addAttribute("isAdmin", authServiceImpl.isAdmin(authentication));
        model.addAttribute("query", query);
        return "articles/list";
    }

    @GetMapping("/guest")
    public String guestArticles(Model model) {
        List<Article> articles = articleService.getAllArticlesSorted();
        model.addAttribute("articles", articles);
        return "articles/list_guest";
    }






}

