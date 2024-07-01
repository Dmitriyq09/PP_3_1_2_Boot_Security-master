package ru.kata.spring.boot_security.demo.creator;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;


import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;


@Component
public class Init {
    private final UserService userService;
    private final RoleService roleService;

    public Init(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void initializeDefaultUsersAndRole() {
        Role admin = new Role("ROLE_ADMIN");
        Role user = new Role("ROLE_USER");

        roleService.addRole(user);
        roleService.addRole(admin);

        Set<Role> adminRoles = new HashSet<>();
        Set<Role> userRoles = new HashSet<>();

        userRoles.add(roleService.findRoleById(1L).get());
        adminRoles.add(roleService.findRoleById(1L).get());
        adminRoles.add(roleService.findRoleById(2L).get());

        User user1 = new User("Admin", "Adminich", "admin", "admin");
        User user2 = new User("User", "Userovich", "user", "user");

        user1.setRoles(adminRoles);
        user2.setRoles(userRoles);

        userService.addUser(user1);
        userService.addUser(user2);

    }
}