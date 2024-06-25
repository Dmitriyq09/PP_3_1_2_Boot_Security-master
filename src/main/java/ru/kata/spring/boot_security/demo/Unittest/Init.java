package ru.kata.spring.boot_security.demo.Unittest;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Optional;

@Component
public class Init {

    private RoleRepository roleRepository;
    private UserService userService;

    public Init(RoleRepository roleRepository, UserService userService) {
        this.roleRepository = roleRepository;
        this.userService = userService;
    }


    @Transactional
    @PostConstruct
    public void initUsers() {

        createRoleIfNotFound("ROLE_ADMIN");
        createRoleIfNotFound("ROLE_USER");

        Optional<Role> adminRoleOpt = roleRepository.findByName("ROLE_ADMIN");
        Optional<Role> userRoleOpt = roleRepository.findByName("ROLE_USER");

        if (adminRoleOpt.isPresent() && userRoleOpt.isPresent()) {
            Role adminRole = adminRoleOpt.get();
            Role userRole = userRoleOpt.get();

            User admin = new User("admin", "100");
            admin.setRoles(Collections.singletonList(adminRole));
            userService.saveUser(admin);

            User user = new User("user", "100");
            user.setRoles(Collections.singletonList(userRole));
            userService.saveUser(user);
        }
    }

    private void createRoleIfNotFound(String name) {
        Optional<Role> roleOpt = roleRepository.findByName(name);
        if (!roleOpt.isPresent()) {
            Role role = new Role(name);
            roleRepository.save(role);
        }
    }
}
