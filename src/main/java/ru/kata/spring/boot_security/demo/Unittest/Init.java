package ru.kata.spring.boot_security.demo.Unittest;

import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Component
public class Init {

    private final UserService userService;

    public Init(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void initUsers() {

            User admin = new User("admin", "100", "ADMIN");
            User user = new User("user", "100", "USER");

            userService.saveUser(admin);
            userService.saveUser(user);
    }
}
