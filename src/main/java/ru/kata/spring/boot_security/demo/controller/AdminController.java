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
    public String registrationPage(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        return "redirect:/admin"; // Шаблон Thymeleaf
    }

    @PostMapping("/save")
    public String performRegistration(@ModelAttribute("user") @Valid User user,
                                      BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", roleService.getAllRoles());
            return "admin"; // Имя шаблона Thymeleaf для отображения формы
        }
        userService.saveUser(user);
        return "redirect:/admin"; // Перенаправление на список пользователей
    }


    @GetMapping
    public String allUsers(Model model, Principal principal) {
        User userFrom = userService.getUserByUsername(principal.getName());
        model.addAttribute("userFrom", userFrom);
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin"; // Шаблон Thymeleaf
    }


    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", user.getRoles());
        return "redirect:/admin"; // Шаблон Thymeleaf
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin/edit"; // Имя шаблона Thymeleaf для отображения формы редактирования
    }


    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("user") @Valid User user, @PathVariable("id") Long id,
                         BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", roleService.getAllRoles());
            return "admin"; // Имя шаблона Thymeleaf для отображения формы
        }
        userService.updateUser(user, id);
        return "redirect:/admin"; // Перенаправление на список пользователей
    }


    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin"; // Перенаправление на список пользователей
    }

}
