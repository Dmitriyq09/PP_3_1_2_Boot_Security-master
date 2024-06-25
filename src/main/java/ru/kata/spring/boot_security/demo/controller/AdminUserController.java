package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.service.imp.UserServiceImpl;


import javax.validation.Valid;
import java.util.Optional;

@Controller
public class AdminUserController {
    private final UserServiceImpl userService;

    public AdminUserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/add_new_user")
    public String addNewUser(Model model) {
        User user = new User("admin", "password");
        model.addAttribute("user", user);
        return "user_info";
    }

    @PostMapping("/admin/save_user")
    public String saveUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "user_info";
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/update")
    public String updateUser(@RequestParam("id") long id, Model model) {
        Optional<User> user = userService.getUser(id);
        if (user.isPresent()) {
            model.addAttribute("user", user);
            return "user_info";
        } else throw new UsernameNotFoundException(String.format("Username with id '%s' not found", id));
    }

    @GetMapping("/admin/delete")
    public String deleteUser(@RequestParam("id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}