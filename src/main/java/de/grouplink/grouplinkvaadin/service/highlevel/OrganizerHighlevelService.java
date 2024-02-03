package de.grouplink.grouplinkvaadin.service.highlevel;

import de.grouplink.grouplinkvaadin.data.dto.ApplicationUserDTO;
import de.grouplink.grouplinkvaadin.data.dto.EventDTO;
import de.grouplink.grouplinkvaadin.security.AuthenticatedUserHelper;
import de.grouplink.grouplinkvaadin.service.lowlevel.EventLowLevelService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Validated
@Service
@Slf4j
public class OrganizerHighlevelService {
    // SERVICES
    private final EventLowLevelService eventLowLevelService;

    // UTILS
    private final AuthenticatedUserHelper authenticatedUserHelper;

    public OrganizerHighlevelService(
            EventLowLevelService eventLowLevelService,
            AuthenticatedUserHelper authenticatedUserHelper
    ) {
        this.eventLowLevelService = eventLowLevelService;
        this.authenticatedUserHelper = authenticatedUserHelper;
    }

    // EVENT SECTION
    public EventDTO createEventForCurrentUser(
            @NotBlank String name,
            @NotNull String description
    ) {
        Optional<ApplicationUserDTO> currentUserOptional = authenticatedUserHelper.get();
        if (currentUserOptional.isEmpty()) {
            throw new IllegalStateException("No user authenticated.");
        }
        ApplicationUserDTO currentUser = currentUserOptional.get();
        log.info("Trying to create event for user with id: {}", currentUser.getId());
        EventDTO createdEvent = eventLowLevelService.createNewEvent(
                name,
                description,
                ZonedDateTime.now(),
                null,
                currentUser.getId()
        );
        log.info("Event (ID: {}) created for user with id: {}", createdEvent.getId(), currentUser.getId());
        return createdEvent;
    }

    public List<EventDTO> getAllEventsForCurrentUser() {
        Optional<ApplicationUserDTO> currentUserOptional = authenticatedUserHelper.get();
        if (currentUserOptional.isEmpty()) {
            throw new IllegalStateException("No user authenticated.");
        }
        ApplicationUserDTO currentUser = currentUserOptional.get();
        log.info("Trying to get events for user with id: {}", currentUser.getId());
        List<EventDTO> events = eventLowLevelService.getAllForOwningUser(currentUser.getId());
        log.info("Got {} events for user with id: {}", events.size(), currentUser.getId());
        return events;
    }
}
