package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getAllRoles() {
        return (List<Role>) roleRepository.findAll();
    }

    @Override
    public Optional<Role> getUserById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public void addRole(Role role) {
//        if (checkRolesExist(role)) {
//            return;
//        }
        roleRepository.save(role);
    }

//    private boolean checkRolesExist(Role role) {
//        Iterable<Role> roleIterator = roleRepository.findAll();
//
//        while (roleIterator.iterator().hasNext()) {
//            Role iterRole = roleIterator.iterator().next();
//            if (iterRole.getRole().equals(role.getRole())) return true;
//        }
//
//        return false;
//    }
}
