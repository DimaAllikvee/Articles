package com.example.Articles.Controller;


import com.example.Articles.ServiceImpl.AuthServiceImpl;
import com.example.Articles.entity.User;
import com.example.Articles.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final AuthServiceImpl authServiceImpl;

    @Autowired
    public UserController(UserService userService, AuthServiceImpl authServiceImpl) {
        this.userService = userService;
        this.authServiceImpl = authServiceImpl;
    }

    @GetMapping
    public String getAllUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users/list";
    }

    @GetMapping("/{id}")
    public String getUserInfoById(@PathVariable Long id, Model model, Authentication authentication) {
        User user = userService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));
        model.addAttribute("author", user);
        model.addAttribute("currentUsername", authServiceImpl.getCurrentUsername(authentication));
        model.addAttribute("isAdmin", authServiceImpl.isAdmin(authentication));

        return "users/details";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        User user = userService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));
        model.addAttribute("user", user);
        return "users/edit";
    }


    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable Long id, User updatedUser) {
        userService.editUser(id, updatedUser);
        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new User());
        return "users/create";
    }

    @PostMapping
    public String createUser(@ModelAttribute User user, Model model) {
        try {
            userService.createUser(user);
            return "redirect:/users";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "users/create";
        }
    }
}
