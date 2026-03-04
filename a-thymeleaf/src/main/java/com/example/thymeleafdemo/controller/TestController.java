package com.example.thymeleafdemo.controller;

import com.example.thymeleafdemo.model.Test;
import com.example.thymeleafdemo.service.TestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tests")
public class TestController {

    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    // Display list of tests
    @GetMapping
    public String listTests(Model model) {
        model.addAttribute("tests", testService.getAllTests());
        return "tests/list";
    }

    // Add new test - TEACHER only
    @GetMapping("/new")
    public String newTestForm(Model model) {
        model.addAttribute("action", "Добавить тест");
        return "tests/form";
    }

    @PostMapping("/new")
    public String createTest(@RequestParam String name, @RequestParam String subject, @RequestParam String description) {
        Test test = new Test();
        test.setName(name);
        test.setSubject(subject);
        test.setDescription(description);
        testService.save(test);
        return "redirect:/tests";
    }

    // Take test - STUDENT only
    @GetMapping("/{id}/take")
    public String takeTest(@PathVariable Long id, Model model) {
        Test test = testService.findById(id).orElse(null);
        model.addAttribute("action", "Пройти тест");
        model.addAttribute("test", test);
        model.addAttribute("id", id);
        return "tests/take";
    }

    @PostMapping("/{id}/take")
    public String submitTest(@PathVariable Long id, @RequestParam String answers) {
        // Logic to submit test
        return "redirect:/tests";
    }
}
