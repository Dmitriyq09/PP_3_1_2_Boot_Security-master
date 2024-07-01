package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
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


    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;

    }
    @GetMapping("/new")
    public String addUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        return "registration"; // Шаблон Thymeleaf
    }

    @PostMapping
    public String performRegistration(@ModelAttribute("user") @Valid User user,
                                      BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", roleService.getAllRoles());
            return "registration"; // Имя шаблона Thymeleaf для отображения формы
        }
        userService.addUser(user);
        return "redirect:/admin"; // Перенаправление на список пользователей
    }


    @GetMapping
    public String getAdminPage(@ModelAttribute("user") User user,Model model, Principal principal) {
        User userFromDB  = userService.findByUsername(principal.getName());
        model.addAttribute("userFromDB", userFromDB);
        model.addAttribute("roleOfUserFromDB", userFromDB.getRoles());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin"; // Шаблон Thymeleaf
    }


    @GetMapping("/{id}/page")
    public String getUserById(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", user.getRoles());
        return "page"; // Шаблон Thymeleaf
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.getAllRoles());
        return "edit"; // Имя шаблона Thymeleaf для отображения формы редактирования
    }


    @PostMapping("/update/{id}")
    public String updateUser(@ModelAttribute("user") @Valid User user, @PathVariable("id") Long id,
                         BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", roleService.getAllRoles());
            return "edit"; // Имя шаблона Thymeleaf для отображения формы
        }
        userService.updateUser(user, id);
        return "redirect:/admin"; // Перенаправление на список пользователей
    }


    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin"; // Перенаправление на список пользователей
    }

}