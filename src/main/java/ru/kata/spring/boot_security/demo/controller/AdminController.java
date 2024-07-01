package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/{id}/panel")
    public String showUserPanel(@PathVariable("id") Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", user.getRoles());
        return "panel"; // метод возвращает панель пользователя по ID
    }

    @GetMapping("/editUser/{id}")
    public String showEditUserForm(@PathVariable("id") Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.getAllRoles());
        return "editUser"; // метод отображает форму для редактирования пользователя
    }

    @GetMapping("/new")
    public String showNewUserForm (@ModelAttribute("user") User user, Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin"; // Шаблон Thymeleaf
    } //метод отображает форму для добавления нового пользователя


    @PostMapping("/save")

    public String saveNewUser (@ModelAttribute("user") @Valid User user,
                               BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", roleService.getAllRoles());
            return "redirect:/admin"; // Имя шаблона Thymeleaf для отображения формы
        }
        userService.saveNewUser (user);
        return "redirect:/admin"; // Перенаправление на список пользователей
    } //метод обрабатывает регистрацию нового пользователя



    @GetMapping
    public String showAdminDashboard(@ModelAttribute("user") User user,Model model, Principal principal) {
        User userFromDB  = userService.getUserByUsername(principal.getName());
        model.addAttribute("userFromDB", userFromDB);
        model.addAttribute("roleOfUserFromDB", userFromDB.getRoles());
        model.addAttribute("users", userService.findAllUsers());
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin"; // метод возвращает главную страницу администратора
    }


    @PostMapping("/updateUser/{id}")
    public String processUserUpdate(@ModelAttribute("user") @Valid User user) {
        userService.updateExistingUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/deleteUser/{id}")
    public String processUserDeletion(@PathVariable("id") Long id) {
        userService.removeUserById(id);
        return "redirect:/admin"; // метод удаляет пользователя.
    }


}
