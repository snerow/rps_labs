package com.example.thymeleafdemo.controller;

import com.example.thymeleafdemo.model.SignupForm;
import com.example.thymeleafdemo.model.User;
import com.example.thymeleafdemo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SignupController {

    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    // Signup GET
    @GetMapping("/signup")
    public String signupPage(Model model) {
        model.addAttribute("signupForm", new SignupForm());
        return "signup";
    }

    // Signup POST
    @PostMapping("/signup")
    public String signupAction(@ModelAttribute SignupForm signupForm) {
        // Validate: username exists
        if (userService.existsByUsername(signupForm.getUsername())) {
            return "redirect:/signup?error=usernameExists";
        }

        // Validate: passwords match
        if (!signupForm.getPassword().equals(signupForm.getConfirmPassword())) {
            return "redirect:/signup?error=passwordMismatch";
        }

        // Validate: agreed to terms
        if (!signupForm.isAgreedToTerms()) {
            return "redirect:/signup?error=terms";
        }

        // Create and save new user
        User user = new User();
        user.setUsername(signupForm.getUsername());
        user.setPassword(signupForm.getPassword());
        user.setEmail(signupForm.getEmail());
        user.setDateOfBirth(signupForm.getDateOfBirth());
        user.setAge(signupForm.getAge());
        user.setGender(signupForm.getGender());
        user.getAuthorities().add("USER");

        userService.save(user);

        return "redirect:/login";
    }
}
