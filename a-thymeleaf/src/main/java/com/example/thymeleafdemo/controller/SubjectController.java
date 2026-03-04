package com.example.thymeleafdemo.controller;

import com.example.thymeleafdemo.model.Subject;
import com.example.thymeleafdemo.service.SubjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    // Display list of subjects
    @GetMapping
    public String listSubjects(Model model) {
        model.addAttribute("subjects", subjectService.getAllSubjects());
        return "subjects/list";
    }

    // Add new subject - TEACHER only
    @GetMapping("/new")
    public String newSubjectForm(Model model) {
        model.addAttribute("action", "Добавить предмет");
        return "subjects/form";
    }

    @PostMapping("/new")
    public String createSubject(@RequestParam String name, @RequestParam String description) {
        Subject subject = new Subject();
        subject.setName(name);
        subject.setDescription(description);
        subjectService.save(subject);
        return "redirect:/subjects";
    }
}
