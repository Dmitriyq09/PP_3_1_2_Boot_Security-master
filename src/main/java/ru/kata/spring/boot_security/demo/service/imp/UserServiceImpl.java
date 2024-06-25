package ru.kata.spring.boot_security.demo.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.service.UserService;


import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public User findByUsername(String name) {
        return (User) userRepository.findByUsername(name);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUser(long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void saveRole(Role role) {
        roleRepository.save(role);
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        if (user.getId() == null) {
            throw new IllegalArgumentException("User must have an id for update");
        }
        if (!userRepository.existsById(user.getId())) {
            throw new IllegalArgumentException("User not found with id: " + user.getId());
        }
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        if (findByUsername(username) == null){
            throw new UsernameNotFoundException(String.format("User not found with username:"+ username));
        }
        return findByUsername(username);
    }
}
