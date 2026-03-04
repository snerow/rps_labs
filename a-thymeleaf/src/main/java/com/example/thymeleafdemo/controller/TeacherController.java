package com.example.thymeleafdemo.controller;

import com.example.thymeleafdemo.model.Teacher;
import com.example.thymeleafdemo.service.TeacherService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherService teacherService;
    private final UserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;

    public TeacherController(TeacherService teacherService, UserDetailsManager userDetailsManager, PasswordEncoder passwordEncoder) {
        this.teacherService = teacherService;
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }

    // Display list of teachers
    @GetMapping
    public String listTeachers(Model model) {
        model.addAttribute("teachers", teacherService.getAllTeachers());
        return "teachers/list";
    }

    // Add new teacher - MANAGER only
    @GetMapping("/new")
    public String newTeacherForm(Model model) {
        model.addAttribute("action", "Добавить учителя");
        return "teachers/form";
    }

    @PostMapping("/new")
    public String createTeacher(@RequestParam String username,
                                @RequestParam String password,
                                @RequestParam String name,
                                @RequestParam String email) {
        // Create new teacher user in Spring Security
        var user = org.springframework.security.core.userdetails.User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .roles("TEACHER")
                .build();
        userDetailsManager.createUser(user);

        // Also add to teacher service for display
        Teacher teacher = new Teacher();
        teacher.setUsername(username);
        teacher.setName(name);
        teacher.setEmail(email);
        teacherService.save(teacher);

        return "redirect:/teachers";
    }

    // Edit teacher profile - MANAGER only
    @GetMapping("/{id}/edit")
    public String editTeacherForm(@PathVariable Long id, Model model) {
        Teacher teacher = teacherService.findById(id).orElse(null);
        model.addAttribute("action", "Изменить профиль учителя");
        model.addAttribute("teacher", teacher);
        return "teachers/form";
    }

    @PostMapping("/{id}/edit")
    public String updateTeacher(@PathVariable Long id, @RequestParam String name, @RequestParam String email) {
        Teacher teacher = new Teacher();
        teacher.setId(id);
        teacher.setName(name);
        teacher.setEmail(email);
        teacherService.update(teacher);
        return "redirect:/teachers";
    }
}
