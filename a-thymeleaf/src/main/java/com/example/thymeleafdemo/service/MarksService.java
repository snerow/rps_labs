package com.example.thymeleafdemo.service;

import com.example.thymeleafdemo.model.Marks;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class MarksService {

    private final Map<Long, Marks> marksMap = new HashMap<>();

    public MarksService() {
        // Pre-populate with demo marks for existing students
        marksMap.put(1L, new Marks(1L, 85, 90, 78));
        marksMap.put(2L, new Marks(2L, 92, 88, 95));
        marksMap.put(3L, new Marks(3L, 76, 82, 80));
        marksMap.put(4L, new Marks(4L, 88, 91, 85));
    }

    public Optional<Marks> findByStudentId(Long studentId) {
        return Optional.ofNullable(marksMap.get(studentId));
    }

    public void save(Marks marks) {
        marksMap.put(marks.getStudentId(), marks);
    }

    public void updateMarks(Long studentId, int math, int physics, int chemistry) {
        Marks marks = marksMap.getOrDefault(studentId, new Marks());
        marks.setStudentId(studentId);
        marks.setMath(math);
        marks.setPhysics(physics);
        marks.setChemistry(chemistry);
        marksMap.put(studentId, marks);
    }
}
