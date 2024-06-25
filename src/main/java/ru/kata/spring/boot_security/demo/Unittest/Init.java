package ru.kata.spring.boot_security.demo.Unittest;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.Collections;


@Component
public class Init {

    private final RoleRepository roleRepository;
    private final UserService userService;

    public Init(RoleRepository roleRepository, UserService userService) {
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @Transactional
    @PostConstruct
    public void initUsers() {
        Role adminRole = createRoleIfNotFound("ROLE_ADMIN");
        Role userRole = createRoleIfNotFound("ROLE_USER");

        createUserIfNotFound("admin", adminRole);
        createUserIfNotFound("user", userRole);
    }

    private Role createRoleIfNotFound(String name) {
        return roleRepository.findByName(name).orElseGet(() -> {
            Role newRole = new Role(name);
            return roleRepository.save(newRole);
        });
    }

    private void createUserIfNotFound(String username, Role role) {
        userService.findByUsername(username).orElseGet(() -> {
            User newUser = new User(username, "100");
            newUser.setRoles(Collections.singletonList(role));
            return userService.saveUser(newUser);
        });
    }
}
