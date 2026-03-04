package com.example.thymeleafdemo.repository;

import com.example.thymeleafdemo.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    List<Registration> findByEventId(Long eventId);
    List<Registration> findByUserId(String userId);
    Optional<Registration> findByUserIdAndEventId(String userId, Long eventId);

    @Query("SELECT COUNT(r) FROM Registration r WHERE r.event.id = :eventId")
    Long countByEventId(@Param("eventId") Long eventId);

    @Query("SELECT r FROM Registration r JOIN FETCH r.event WHERE r.userId = :userId")
    List<Registration> findByUserIdWithEvents(@Param("userId") String userId);
}
