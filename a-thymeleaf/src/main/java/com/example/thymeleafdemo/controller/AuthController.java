package com.example.thymeleafdemo.controller;

import com.example.thymeleafdemo.model.LoginForm;
import com.example.thymeleafdemo.model.User;
import com.example.thymeleafdemo.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // Login GET
    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    // Login POST
    @PostMapping("/login")
    public String loginAction(@ModelAttribute LoginForm loginForm,
                              HttpSession session) {
        if (userService.authenticate(loginForm.getUsername(), loginForm.getPassword())) {
            User user = userService.findByUsername(loginForm.getUsername()).orElse(null);
            session.setAttribute("user", user);
            return "redirect:/";
        }
        return "redirect:/login?error";
    }

    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
