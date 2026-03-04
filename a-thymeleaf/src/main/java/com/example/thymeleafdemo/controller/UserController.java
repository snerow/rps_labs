package com.example.thymeleafdemo.controller;

import com.example.thymeleafdemo.model.User;
import com.example.thymeleafdemo.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Users list GET
    @GetMapping
    public String usersPage(Model model, HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    // Delete user POST
    @PostMapping("/delete")
    public String deleteUser(@RequestParam String username, HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        userService.deleteByUsername(username);
        return "redirect:/users";
    }

    // Modify user GET
    @GetMapping("/modify")
    public String modifyUserPage(@RequestParam String username,
                                 Model model,
                                 HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        Optional<User> userOpt = userService.findByUsername(username);
        if (userOpt.isEmpty()) {
            return "redirect:/users";
        }
        model.addAttribute("modifyUser", userOpt.get());
        return "modify-user";
    }

    // Add authority POST
    @PostMapping("/authority/add")
    public String addAuthority(@RequestParam String username,
                               @RequestParam String authority,
                               HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        if (authority != null && !authority.isBlank()) {
            userService.findByUsername(username).ifPresent(user -> {
                if (!user.getAuthorities().contains(authority)) {
                    user.getAuthorities().add(authority);
                }
            });
        }
        return "redirect:/users/modify?username=" + username;
    }

    // Delete authority POST
    @PostMapping("/authority/delete")
    public String deleteAuthority(@RequestParam String username,
                                  @RequestParam String authority,
                                  HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        userService.findByUsername(username).ifPresent(user ->
                user.getAuthorities().remove(authority)
        );
        return "redirect:/users/modify?username=" + username;
    }
}
