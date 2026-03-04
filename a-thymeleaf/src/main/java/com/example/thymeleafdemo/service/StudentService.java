package com.example.thymeleafdemo.service;

import com.example.thymeleafdemo.model.Student;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final List<Student> students = new ArrayList<>();
    private Long nextId = 1L;

    public StudentService() {
        // Pre-populate with demo students
        students.add(new Student(nextId++, "student", "Смирнов Алексей Дмитриевич", "student@school.ru"));
        students.add(new Student(nextId++, "kozlova", "Козлова Мария Сергеевна", "kozlova@school.ru"));
        students.add(new Student(nextId++, "novikov", "Новиков Дмитрий Андреевич", "novikov@school.ru"));
        students.add(new Student(nextId++, "morozova", "Морозова Екатерина Павловна", "morozova@school.ru"));
    }

    public List<Student> getAllStudents() {
        return students;
    }

    public Optional<Student> findById(Long id) {
        return students.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst();
    }

    public void save(Student student) {
        if (student.getId() == null) {
            student.setId(nextId++);
        }
        students.add(student);
    }

    public void update(Student student) {
        findById(student.getId()).ifPresent(existing -> {
            existing.setName(student.getName());
            existing.setEmail(student.getEmail());
        });
    }

    public void deleteById(Long id) {
        students.removeIf(s -> s.getId().equals(id));
    }
}
