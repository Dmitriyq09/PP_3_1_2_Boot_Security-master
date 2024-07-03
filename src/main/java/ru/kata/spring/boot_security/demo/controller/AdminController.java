package ru.kata.spring.boot_security.demo.controller;


import org.hibernate.Hibernate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;


import java.security.Principal;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<User>> listAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers()); // Возвращает список всех пользователей
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable("id") Long id) {
        User user = userService.getUser(id);  // Находит пользователя по ID
        Hibernate.initialize(user.getRoles());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/show")
    public ResponseEntity<User> showCurrentUser(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return new ResponseEntity<>(user, HttpStatus.OK);  // Показывает информацию о текущем пользователе
    }

    @GetMapping("/roles")
    public ResponseEntity<Collection<Role>> listAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());  // Возвращает список всех ролей
    }

    @PostMapping
    public ResponseEntity<User> registerNewUser(@RequestBody User user) {
        User savedUser = userService.addUser(user); // Регистрирует нового пользователя
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<User> updateUserDetails(@RequestBody User user) {
        User updatedUser = userService.updateUser(user); // Редактирует информацию о пользователе
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUserById(@PathVariable Long id) {
        userService.deleteUser(id);   // Удаляет пользователя по ID
        return ResponseEntity.ok(HttpStatus.OK);
    }

}