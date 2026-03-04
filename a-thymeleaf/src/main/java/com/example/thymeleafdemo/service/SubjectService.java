package com.example.thymeleafdemo.service;

import com.example.thymeleafdemo.model.Subject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {

    private final List<Subject> subjects = new ArrayList<>();
    private Long nextId = 1L;

    public SubjectService() {
        // Pre-populate with demo subjects
        subjects.add(new Subject(nextId++, "Математика", "Алгебра и геометрия"));
        subjects.add(new Subject(nextId++, "Физика", "Механика и термодинамика"));
        subjects.add(new Subject(nextId++, "Химия", "Органическая и неорганическая"));
        subjects.add(new Subject(nextId++, "Биология", "Анатомия и экология"));
        subjects.add(new Subject(nextId++, "История", "Всемирная история"));
    }

    public List<Subject> getAllSubjects() {
        return subjects;
    }

    public Optional<Subject> findById(Long id) {
        return subjects.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst();
    }

    public void save(Subject subject) {
        if (subject.getId() == null) {
            subject.setId(nextId++);
        }
        subjects.add(subject);
    }

    public void deleteById(Long id) {
        subjects.removeIf(s -> s.getId().equals(id));
    }
}
