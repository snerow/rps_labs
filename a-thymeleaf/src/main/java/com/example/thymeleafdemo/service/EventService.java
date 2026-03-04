package com.example.thymeleafdemo.service;

import com.example.thymeleafdemo.entity.Event;
import com.example.thymeleafdemo.entity.Registration;
import com.example.thymeleafdemo.repository.EventRepository;
import com.example.thymeleafdemo.repository.RegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final RegistrationRepository registrationRepository;
    private final RegistrationService registrationService;
    private final LoggingService loggingService;

    public EventService(EventRepository eventRepository,
                        RegistrationRepository registrationRepository,
                        RegistrationService registrationService,
                        LoggingService loggingService) {
        this.eventRepository = eventRepository;
        this.registrationRepository = registrationRepository;
        this.registrationService = registrationService;
        this.loggingService = loggingService;
    }

    /**
     * Add new event - NOT_SUPPORTED propagation
     * NOT_SUPPORTED - executes without transaction context
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Event addEvent(String title, String description, LocalDateTime eventDate, Integer maxParticipants) {
        Event event = new Event(title, description, eventDate, maxParticipants);
        Event saved = eventRepository.save(event);

        // Log without transaction
        loggingService.logWithoutTransaction("Event created: " + title);

        return saved;
    }

    /**
     * Register user for event - main transactional method
     * REQUIRED - creates new transaction if none exists
     * SERIALIZABLE - highest isolation level
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public Registration registerUserForEvent(String userId, Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        // Check if event has available slots
        if (!event.hasAvailableSlots()) {
            throw new RuntimeException("Event is full");
        }

        // Create registration
        Registration registration = new Registration();
        registration.setUserId(userId);
        registration.setEvent(event);
        registration.setRegistrationDate(LocalDateTime.now());
        registration.setStatus("ACTIVE");

        Registration saved = registrationRepository.save(registration);

        // Log in separate transaction (REQUIRES_NEW)
        loggingService.logOperation("REGISTER_USER_FOR_EVENT", "Event", eventId,
                "User " + userId + " registered. Total registrations: " + (event.getRegisteredCount() + 1));

        return saved;
    }

    /**
     * Remove user registration - uses MANDATORY propagation
     * This method must be called within an existing transaction
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void removeUserRegistration(Long registrationId) {
        // Calls registrationService with MANDATORY propagation
        registrationService.removeRegistration(registrationId);
    }

    /**
     * Get registered users for event
     * REQUIRED - uses existing transaction or creates new one
     * READ_COMMITTED - reads only committed data
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public List<Registration> getRegisteredUsers(Long eventId) {
        return registrationService.getRegistrationsByEvent(eventId);
    }

    /**
     * Update event details with nested transaction for registrations
     * REQUIRED - main transaction
     * REPEATABLE_READ - prevents non-repeatable reads
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public Event updateEventDetails(Long eventId, String newTitle, String newDescription) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        event.setTitle(newTitle);
        event.setDescription(newDescription);
        Event updated = eventRepository.save(event);

        // Update all registrations for this event (nested transaction)
        List<Registration> registrations = registrationRepository.findByEventId(eventId);
        for (Registration reg : registrations) {
            // NESTED propagation - creates savepoint
            registrationService.updateRegistration(reg.getId(), "UPDATED");
        }

        loggingService.logOperation("UPDATE_EVENT", "Event", eventId,
                "Event updated: " + newTitle);

        return updated;
    }

    /**
     * Non-transactional operation
     */
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    /**
     * Find events after date - read-only transaction
     */
    @Transactional(readOnly = true)
    public List<Event> getUpcomingEvents() {
        return eventRepository.findByEventDateAfter(LocalDateTime.now());
    }
}
