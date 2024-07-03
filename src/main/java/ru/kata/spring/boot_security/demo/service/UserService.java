package ru.kata.spring.boot_security.demo.service;


import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.entities.User;

import java.util.List;
import java.util.Optional;


@Service
public interface UserService {
    List<User> getAllUsers();

    User getUser(Long id);

    User addUser(User user);

    void deleteUser(Long id);

    User updateUser(User user);

    User findByUsername(String username);

    User findById(Long id);

}
