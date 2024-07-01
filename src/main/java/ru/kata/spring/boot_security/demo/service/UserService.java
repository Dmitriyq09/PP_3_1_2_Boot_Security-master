package ru.kata.spring.boot_security.demo.service;


import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.entities.User;

import java.util.List;


@Service
public interface UserService {
    void saveNewUser (User user);

    void updateExistingUser(User user);

    List<User> findAllUsers();

    User getUserById(Long id);

    User getUserByUsername(String username);

    void removeUserById(Long id);

}
