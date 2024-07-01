package ru.kata.spring.boot_security.demo.service;


import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.entities.User;

import java.util.List;


@Service
public interface UserService {
    void addUser(User user);
    User getUser(Long id);

    void updateUser(User user, Long id);

    List<User> getAllUsers();

    User findRoleById(Long id);

    User findByUsername(String username);
    User findById(Long id);

    void deleteUser(Long id);

}
