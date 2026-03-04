package com.example.thymeleafdemo.controller;

import com.example.thymeleafdemo.config.UserDetailsConfig;
import com.example.thymeleafdemo.model.Marks;
import com.example.thymeleafdemo.model.Student;
import com.example.thymeleafdemo.service.MarksService;
import com.example.thymeleafdemo.service.StudentService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final UserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;
    private final StudentService studentService;
    private final MarksService marksService;

    public StudentController(UserDetailsManager userDetailsManager, PasswordEncoder passwordEncoder, StudentService studentService, MarksService marksService) {
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
        this.studentService = studentService;
        this.marksService = marksService;
    }

    // Display list of students
    @GetMapping
    public String listStudents(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        return "students/list";
    }

    // Add new student - MANAGER or TEACHER
    @GetMapping("/new")
    public String newStudentForm(Model model) {
        model.addAttribute("action", "Добавить ученика");
        return "students/new";
    }

    @PostMapping("/new")
    public String createStudent(@RequestParam String username,
                                @RequestParam String password,
                                @RequestParam String name) {
        // Create new student user in Spring Security
        var user = org.springframework.security.core.userdetails.User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .roles("STUDENT")
                .build();
        userDetailsManager.createUser(user);

        // Also add to student service for display
        Student student = new Student();
        student.setUsername(username);
        student.setName(name);
        student.setEmail(username + "@school.ru");
        studentService.save(student);

        return "redirect:/students";
    }

    // Edit student profile - MANAGER only
    @GetMapping("/{id}/edit")
    public String editStudentForm(@PathVariable Long id, Model model) {
        Student student = studentService.findById(id).orElse(null);
        model.addAttribute("action", "Изменить профиль ученика");
        model.addAttribute("student", student);
        return "students/form";
    }

    @PostMapping("/{id}/edit")
    public String updateStudent(@PathVariable Long id, @RequestParam String name, @RequestParam String email) {
        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setEmail(email);
        studentService.update(student);
        return "redirect:/students";
    }

    // View student marks - STUDENT, TEACHER, MANAGER
    @GetMapping("/{id}/marks")
    public String viewMarks(@PathVariable Long id, Model model) {
        Student student = studentService.findById(id).orElse(null);
        Marks marks = marksService.findByStudentId(id).orElse(new Marks(id, 0, 0, 0));
        model.addAttribute("student", student);
        model.addAttribute("marks", marks);
        model.addAttribute("id", id);
        return "students/marks";
    }

    // Edit student marks - MANAGER or TEACHER
    @GetMapping("/{id}/marks/edit")
    public String editMarksForm(@PathVariable Long id, Model model) {
        Student student = studentService.findById(id).orElse(null);
        Marks marks = marksService.findByStudentId(id).orElse(new Marks(id, 85, 90, 78));
        model.addAttribute("action", "Изменить оценки");
        model.addAttribute("student", student);
        model.addAttribute("marks", marks);
        model.addAttribute("id", id);
        return "students/marks-edit";
    }

    @PostMapping("/{id}/marks/edit")
    public String updateMarks(@PathVariable Long id, @RequestParam int math, @RequestParam int physics, @RequestParam int chemistry) {
        // Save marks to the service
        marksService.updateMarks(id, math, physics, chemistry);
        System.out.println("Updated marks for student " + id + ": Math=" + math + ", Physics=" + physics + ", Chemistry=" + chemistry);
        return "redirect:/students/" + id + "/marks";
    }
}
