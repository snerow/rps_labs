package com.example.thymeleafdemo.service;

import com.example.thymeleafdemo.entity.OperationLog;
import com.example.thymeleafdemo.repository.OperationLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoggingService {

    private final OperationLogRepository operationLogRepository;

    public LoggingService(OperationLogRepository operationLogRepository) {
        this.operationLogRepository = operationLogRepository;
    }

    /**
     * Logs operation in a NEW transaction (independent of main transaction)
     * REQUIRES_NEW - always creates a new transaction, suspends current if exists
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void logOperation(String operation, String entityType, Long entityId, String details) {
        OperationLog log = new OperationLog(operation, entityType, entityId, details);
        operationLogRepository.save(log);
        System.out.println("[LOG] " + operation + " on " + entityType + " (ID: " + entityId + ")");
    }

    /**
     * Non-transactional operation - no transaction context
     */
    public void logWithoutTransaction(String message) {
        System.out.println("[NON-TRANSACTIONAL LOG] " + message);
    }
}
