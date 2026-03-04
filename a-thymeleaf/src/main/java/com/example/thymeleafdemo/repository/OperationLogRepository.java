package com.example.thymeleafdemo.repository;

import com.example.thymeleafdemo.entity.OperationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OperationLogRepository extends JpaRepository<OperationLog, Long> {
    List<OperationLog> findByEntityTypeAndEntityId(String entityType, Long entityId);
    List<OperationLog> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
    List<OperationLog> findByOperation(String operation);
}
