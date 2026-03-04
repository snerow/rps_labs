package com.example.thymeleafdemo.service;

import com.example.thymeleafdemo.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final List<User> users = new ArrayList<>();

    public UserService() {
        // Pre-populate with demo users
        User admin = new User("admin", "admin");
        admin.getAuthorities().add("ADMIN");
        users.add(admin);

        User user = new User("user", "user");
        user.getAuthorities().add("USER");
        users.add(user);
    }

    public List<User> getAllUsers() {
        return users;
    }

    public Optional<User> findByUsername(String username) {
        return users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst();
    }

    public boolean existsByUsername(String username) {
        return users.stream().anyMatch(u -> u.getUsername().equals(username));
    }

    public void save(User user) {
        users.add(user);
    }

    public void deleteByUsername(String username) {
        users.removeIf(u -> u.getUsername().equals(username));
    }

    public boolean authenticate(String username, String password) {
        return users.stream()
                .anyMatch(u -> u.getUsername().equals(username) && u.getPassword().equals(password));
    }
}
