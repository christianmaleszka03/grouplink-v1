package de.grouplink.grouplinkvaadin.service.lowlevel;

import de.grouplink.grouplinkvaadin.data.dto.VoteDTO;
import de.grouplink.grouplinkvaadin.data.entity.GroupEntity;
import de.grouplink.grouplinkvaadin.data.entity.VoteEntity;
import de.grouplink.grouplinkvaadin.data.enums.Gender;
import de.grouplink.grouplinkvaadin.data.repository.EventRepository;
import de.grouplink.grouplinkvaadin.data.repository.GroupRepository;
import de.grouplink.grouplinkvaadin.data.repository.VoteRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.*;

@Service
@Validated
public class VoteLowLevelService {
    private final VoteRepository voteRepository;

    // JUST FOR RETRIEVING ENTITY FROM ID - DO NOT USE FOR ANYTHING ELSE
    private final EventRepository eventRepository;
    private final GroupRepository groupRepository;

    public VoteLowLevelService(
            VoteRepository voteRepository,
            EventRepository eventRepository,
            GroupRepository groupRepository
    ) {
        this.voteRepository = voteRepository;
        this.eventRepository = eventRepository;
        this.groupRepository = groupRepository;
    }

    // CREATE
    public VoteDTO createNewVote(
            @NotBlank String firstName,
            @NotBlank String lastName,
            @NotNull Gender gender,
            @NotNull UUID belongsToEventId,
            UUID assignedToGroupId,
            @NotNull Map<UUID, Integer> groupPreferencesIds
            ) {
        // NEEDED CHECKS
        if(groupPreferencesIds.size() < 2) {
            throw new IllegalArgumentException("The group preferences must contain at least two entries.");
        }

        Set<Integer> occuredValues = new HashSet<>();
        for(Map.Entry<UUID, Integer> entry : groupPreferencesIds.entrySet()) {
            if(entry.getValue() < 0 || entry.getValue() > 10) {
                throw new IllegalArgumentException("The value of the group preference must be between 0 and 10.");
            }
            if(occuredValues.contains(entry.getValue())) {
                throw new IllegalArgumentException("The value of the group preference must be unique.");
            }
            occuredValues.add(entry.getValue());
        }
        for(int i = 1; i <= groupPreferencesIds.size(); i++) {
            if(!occuredValues.contains(i)) {
                throw new IllegalArgumentException("The value of the group preference must be unique.");
            }
        }

        VoteEntity entityToSave = new VoteEntity();

        entityToSave.setFirstName(firstName);
        entityToSave.setLastName(lastName);
        entityToSave.setGender(gender);
        entityToSave.setBelongsToEvent(eventRepository.findById(belongsToEventId).orElseThrow());
        entityToSave.setAssignedToGroup(groupRepository.findById(assignedToGroupId).orElse(null));

        Map<GroupEntity, Integer> groupEntityMap = new HashMap<>();
        for(Map.Entry<UUID, Integer> entry : groupPreferencesIds.entrySet()) {
            GroupEntity groupEntity = groupRepository.findById(entry.getKey()).orElseThrow();
            groupEntityMap.put(groupEntity, entry.getValue());
        }
        entityToSave.setGroupPreferences(groupEntityMap);

        entityToSave.setCantGoWithEachOthers(new ArrayList<>());

        return new VoteDTO(voteRepository.save(entityToSave));
    }

    // READ
    public VoteDTO getById(UUID id) {
        return new VoteDTO(voteRepository.findById(id).orElseThrow());
    }

    public List<VoteDTO> getAllByEvent(@NotNull UUID eventId) {
        return voteRepository.findByBelongsToEvent_Id(eventId).stream().map(VoteDTO::new).toList();
    }

    // UPDATE
    public VoteDTO updateFirstNameForId(@NotNull UUID id, @NotBlank String firstName) {
        VoteEntity entityToUpdate = voteRepository.findById(id).orElseThrow();
        entityToUpdate.setFirstName(firstName);
        return new VoteDTO(voteRepository.save(entityToUpdate));
    }

    public VoteDTO updateLastNameForId(@NotNull UUID id, @NotBlank String lastName) {
        VoteEntity entityToUpdate = voteRepository.findById(id).orElseThrow();
        entityToUpdate.setLastName(lastName);
        return new VoteDTO(voteRepository.save(entityToUpdate));
    }

    public VoteDTO updateGenderForId(@NotNull UUID id, @NotNull Gender gender) {
        VoteEntity entityToUpdate = voteRepository.findById(id).orElseThrow();
        entityToUpdate.setGender(gender);
        return new VoteDTO(voteRepository.save(entityToUpdate));
    }

    public VoteDTO updateAssignedToGroupForId(@NotNull UUID id, UUID assignedToGroupId) {
        VoteEntity entityToUpdate = voteRepository.findById(id).orElseThrow();
        entityToUpdate.setAssignedToGroup(groupRepository.findById(assignedToGroupId).orElse(null));
        return new VoteDTO(voteRepository.save(entityToUpdate));
    }

    public VoteDTO updateGroupPreferencesForId(@NotNull UUID id, @NotNull Map<UUID, Integer> groupPreferencesIds) {
        VoteEntity entityToUpdate = voteRepository.findById(id).orElseThrow();

        // NEEDED CHECKS
        if(groupPreferencesIds.size() < 2) {
            throw new IllegalArgumentException("The group preferences must contain at least two entries.");
        }

        Set<Integer> occuredValues = new HashSet<>();
        for(Map.Entry<UUID, Integer> entry : groupPreferencesIds.entrySet()) {
            if(entry.getValue() < 0 || entry.getValue() > 10) {
                throw new IllegalArgumentException("The value of the group preference must be between 0 and 10.");
            }
            if(occuredValues.contains(entry.getValue())) {
                throw new IllegalArgumentException("The value of the group preference must be unique.");
            }
            occuredValues.add(entry.getValue());
        }
        for(int i = 1; i <= groupPreferencesIds.size(); i++) {
            if(!occuredValues.contains(i)) {
                throw new IllegalArgumentException("The value of the group preference must be unique.");
            }
        }

        Map<GroupEntity, Integer> groupEntityMap = new HashMap<>();
        for(Map.Entry<UUID, Integer> entry : groupPreferencesIds.entrySet()) {
            GroupEntity groupEntity = groupRepository.findById(entry.getKey()).orElseThrow();
            groupEntityMap.put(groupEntity, entry.getValue());
        }
        entityToUpdate.setGroupPreferences(groupEntityMap);

        return new VoteDTO(voteRepository.save(entityToUpdate));
    }

    // DELETE
    // NOT INTENDED YET
}
