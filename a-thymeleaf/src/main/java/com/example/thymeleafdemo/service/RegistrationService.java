package com.example.thymeleafdemo.service;

import com.example.thymeleafdemo.entity.Registration;
import com.example.thymeleafdemo.repository.RegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final LoggingService loggingService;

    public RegistrationService(RegistrationRepository registrationRepository, LoggingService loggingService) {
        this.registrationRepository = registrationRepository;
        this.loggingService = loggingService;
    }

    /**
     * Register user for event with SERIALIZABLE isolation
     * REQUIRED - uses existing transaction or creates new one
     * SERIALIZABLE - highest isolation level, prevents phantom reads
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public Registration registerUser(String userId, Long eventId) {
        // Check if already registered
        if (registrationRepository.findByUserIdAndEventId(userId, eventId).isPresent()) {
            throw new RuntimeException("User already registered for this event");
        }

        Registration registration = new Registration();
        registration.setUserId(userId);
        // Event will be set by EventService
        registration.setRegistrationDate(LocalDateTime.now());
        registration.setStatus("ACTIVE");

        Registration saved = registrationRepository.save(registration);

        // Log in separate transaction (REQUIRES_NEW)
        loggingService.logOperation("REGISTER_USER", "Registration", saved.getId(),
                "User " + userId + " registered for event " + eventId);

        return saved;
    }

    /**
     * Remove registration - MANDATORY propagation
     * MANDATORY - must be called within an existing transaction
     */
    @Transactional(propagation = Propagation.MANDATORY)
    public void removeRegistration(Long registrationId) {
        Registration registration = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Registration not found"));

        registrationRepository.delete(registration);

        loggingService.logOperation("REMOVE_REGISTRATION", "Registration", registrationId,
                "Registration removed for user " + registration.getUserId());
    }

    /**
     * Get registered users with READ_COMMITTED isolation
     * REQUIRED - uses existing transaction or creates new one
     * READ_COMMITTED - reads only committed data
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public List<Registration> getRegistrationsByEvent(Long eventId) {
        return registrationRepository.findByEventId(eventId);
    }

    /**
     * Update registration with NESTED propagation
     * NESTED - executes within a nested transaction (savepoint)
     * REPEATABLE_READ - prevents non-repeatable reads
     */
    @Transactional(propagation = Propagation.NESTED, isolation = Isolation.REPEATABLE_READ)
    public Registration updateRegistration(Long registrationId, String newStatus) {
        Registration registration = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Registration not found"));

        registration.setStatus(newStatus);
        Registration updated = registrationRepository.save(registration);

        loggingService.logOperation("UPDATE_REGISTRATION", "Registration", registrationId,
                "Status changed to " + newStatus);

        return updated;
    }

    /**
     * Non-transactional operation
     */
    public long countRegistrations(Long eventId) {
        return registrationRepository.countByEventId(eventId);
    }
}
