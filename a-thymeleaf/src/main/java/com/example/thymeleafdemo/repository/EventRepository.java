package com.example.thymeleafdemo.repository;

import com.example.thymeleafdemo.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByEventDateAfter(LocalDateTime date);
    List<Event> findByTitleContainingIgnoreCase(String title);
}
