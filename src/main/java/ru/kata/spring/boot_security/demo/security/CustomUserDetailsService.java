package ru.kata.spring.boot_security.demo.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    // Здесь должна быть реализация поиска пользователя в вашей базе данных
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new CustomUserDetails();
    }
}

class CustomUserDetails implements UserDetails {

    // Здесь должны быть поля, конструкторы и методы для вашего пользователя

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        // Вернуть хешированный пароль пользователя
        return null;
    }

    @Override
    public String getUsername() {
        return getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}

class CustomGrantedAuthority implements GrantedAuthority {

    // Здесь должны быть поля, конструкторы и методы для предоставления прав

    @Override
    public String getAuthority() {
        // Вернуть представление права
        return null;
    }
}
