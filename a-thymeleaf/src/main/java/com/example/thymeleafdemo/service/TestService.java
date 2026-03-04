package com.example.thymeleafdemo.service;

import com.example.thymeleafdemo.model.Test;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TestService {

    private final List<Test> tests = new ArrayList<>();
    private Long nextId = 1L;

    public TestService() {
        // Pre-populate with demo tests
        tests.add(new Test(nextId++, "Контрольная по математике", "Математика", "Алгебра - 10 задач"));
        tests.add(new Test(nextId++, "Тест по физике", "Физика", "Механика - 15 вопросов"));
        tests.add(new Test(nextId++, "Экзамен по химии", "Химия", "Органическая химия"));
        tests.add(new Test(nextId++, "Проверочная по истории", "История", "Вторая мировая война"));
    }

    public List<Test> getAllTests() {
        return tests;
    }

    public Optional<Test> findById(Long id) {
        return tests.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst();
    }

    public void save(Test test) {
        if (test.getId() == null) {
            test.setId(nextId++);
        }
        tests.add(test);
    }

    public void deleteById(Long id) {
        tests.removeIf(t -> t.getId().equals(id));
    }
}
