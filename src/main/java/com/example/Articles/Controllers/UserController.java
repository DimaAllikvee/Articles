package com.example.Articles.Controllers;

import com.example.Articles.entity.User;
import com.example.Articles.Interface.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Получить список всех пользователей
    @GetMapping
    public String getAllUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "user/users"; // Шаблон для списка пользователей (src/main/resources/templates/user/users.html)
    }

    // Получить пользователя по ID (детальная страница)
    @GetMapping("/{id}")
    public String getUserById(@PathVariable("id") Long id, Model model) {
        Optional<User> userOpt = userService.getUserById(id);
        if (userOpt.isPresent()) {
            model.addAttribute("user", userOpt.get());
        } else {
            model.addAttribute("errorMessage", "Пользователь не найден");
        }
        return "user/userDetail"; // Шаблон для детальной страницы пользователя
    }

    // Показать форму для создания пользователя
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new User());
        return "user/createUser"; // Шаблон формы создания пользователя
    }

    // Обработка формы создания пользователя
    @PostMapping("/create")
    public String createUser(@ModelAttribute User user) {
        userService.createUser(user);
        return "redirect:/users";
    }

    // Показать форму редактирования пользователя
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Optional<User> userOpt = userService.getUserById(id);
        if (userOpt.isEmpty()) {
            model.addAttribute("errorMessage", "Пользователь не найден");
            return "redirect:/users";
        }
        model.addAttribute("user", userOpt.get());
        return "user/editUser"; // Шаблон формы редактирования пользователя
    }

    // Обработка формы редактирования пользователя
    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable("id") Long id, @ModelAttribute User user) {
        userService.updateUser(id, user);
        return "redirect:/users";
    }

    // Удалить пользователя
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }
}
