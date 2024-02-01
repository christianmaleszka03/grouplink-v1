package de.grouplink.grouplinkvaadin.service.lowlevel;

import de.grouplink.grouplinkvaadin.data.dto.EventDTO;
import de.grouplink.grouplinkvaadin.data.entity.EventEntity;
import de.grouplink.grouplinkvaadin.data.repository.ApplicationUserRepository;
import de.grouplink.grouplinkvaadin.data.repository.EventRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Validated
public class EventLowLevelService {

    private final EventRepository eventRepository;

    // JUST FOR RETRIEVING ENTITY FROM ID - DO NOT USE FOR ANYTHING ELSE
    private final ApplicationUserRepository applicationUserRepository;


    public EventLowLevelService(
            EventRepository eventRepository,
            ApplicationUserRepository applicationUserRepository
    ) {
        this.eventRepository = eventRepository;
        this.applicationUserRepository = applicationUserRepository;
    }

    // CREATE
    public EventDTO createNewEvent(
            @NotBlank String name,
            @NotNull String description,
            @NotNull ZonedDateTime startTimestamp,
            ZonedDateTime endTimestamp,
            @NotNull UUID ownedByUserId
            ) {
        EventEntity entityToSave = new EventEntity();

        entityToSave.setName(name);
        entityToSave.setDescription(description);
        entityToSave.setStartTimestamp(startTimestamp);
        entityToSave.setEndTimestamp(endTimestamp);
        entityToSave.setOwnedByUser(applicationUserRepository.findById(ownedByUserId).orElseThrow());
        entityToSave.setGroups(new ArrayList<>());
        entityToSave.setVotes(new ArrayList<>());

        return new EventDTO(eventRepository.save(entityToSave));
    }

    // READ
    public Optional<EventDTO> getById(@NotNull UUID id){
        return eventRepository.findById(id).map(EventDTO::new);
    }

    public List<EventDTO> getAllForOwningUser(@NotNull UUID ownedByUserId){
        return eventRepository.findByOwnedByUser_Id(ownedByUserId).stream()
                .map(EventDTO::new)
                .toList();
    }

    // UPDATE
    public EventDTO updateNameForId(@NotNull UUID id, @NotBlank String name){
        EventEntity entityToUpdate = eventRepository.findById(id).orElseThrow();
        entityToUpdate.setName(name);
        return new EventDTO(eventRepository.save(entityToUpdate));
    }

    public EventDTO updateDescriptionForId(@NotNull UUID id, @NotBlank String description){
        EventEntity entityToUpdate = eventRepository.findById(id).orElseThrow();
        entityToUpdate.setDescription(description);
        return new EventDTO(eventRepository.save(entityToUpdate));
    }

    public EventDTO updateStartTimestampForId(@NotNull UUID id, @NotNull ZonedDateTime startTimestamp){
        EventEntity entityToUpdate = eventRepository.findById(id).orElseThrow();
        entityToUpdate.setStartTimestamp(startTimestamp);
        return new EventDTO(eventRepository.save(entityToUpdate));
    }

    public EventDTO updateEndTimestampForId(@NotNull UUID id, ZonedDateTime endTimestamp){
        EventEntity entityToUpdate = eventRepository.findById(id).orElseThrow();
        entityToUpdate.setEndTimestamp(endTimestamp);
        return new EventDTO(eventRepository.save(entityToUpdate));
    }

    // DELETE
    // not intended yet
}
