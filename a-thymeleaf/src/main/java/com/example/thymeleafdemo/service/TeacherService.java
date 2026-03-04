package com.example.thymeleafdemo.service;

import com.example.thymeleafdemo.model.Teacher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {

    private final List<Teacher> teachers = new ArrayList<>();
    private Long nextId = 1L;

    public TeacherService() {
        // Pre-populate with demo teachers
        teachers.add(new Teacher(nextId++, "teacher", "Иванов Иван Иванович", "teacher@school.ru"));
        teachers.add(new Teacher(nextId++, "petrov", "Петров Петр Петрович", "petrov@school.ru"));
        teachers.add(new Teacher(nextId++, "sidorova", "Сидорова Анна Владимировна", "sidorova@school.ru"));
    }

    public List<Teacher> getAllTeachers() {
        return teachers;
    }

    public Optional<Teacher> findById(Long id) {
        return teachers.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst();
    }

    public void save(Teacher teacher) {
        if (teacher.getId() == null) {
            teacher.setId(nextId++);
        }
        teachers.add(teacher);
    }

    public void update(Teacher teacher) {
        findById(teacher.getId()).ifPresent(existing -> {
            existing.setName(teacher.getName());
            existing.setEmail(teacher.getEmail());
        });
    }

    public void deleteById(Long id) {
        teachers.removeIf(t -> t.getId().equals(id));
    }
}
