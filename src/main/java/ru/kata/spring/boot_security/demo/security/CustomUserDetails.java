package ru.kata.spring.boot_security.demo.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.kata.spring.boot_security.demo.entities.User;

import java.util.Collection;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Преобразуйте роли пользователя в GrantedAuthority
        return user.getRoles().stream()
                .map(role -> new CustomGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // или возвращайте соответствующее значение из user
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // или возвращайте соответствующее значение из user
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // или возвращайте соответствующее значение из user
    }

    @Override
    public boolean isEnabled() {
        return true; // или возвращайте соответствующее значение из user
    }
}