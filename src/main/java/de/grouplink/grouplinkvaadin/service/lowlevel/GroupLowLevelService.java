package de.grouplink.grouplinkvaadin.service.lowlevel;

import de.grouplink.grouplinkvaadin.data.dto.GroupDTO;
import de.grouplink.grouplinkvaadin.data.entity.GroupEntity;
import de.grouplink.grouplinkvaadin.data.repository.EventRepository;
import de.grouplink.grouplinkvaadin.data.repository.GroupRepository;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Validated
public class GroupLowLevelService {
    private final GroupRepository groupRepository;

    // JUST FOR RETRIEVING ENTITY FROM ID - DO NOT USE FOR ANYTHING ELSE
    private final EventRepository eventRepository;

    public GroupLowLevelService(
            GroupRepository groupRepository,
            EventRepository eventRepository
    ) {
        this.groupRepository = groupRepository;
        this.eventRepository = eventRepository;
    }

    // CREATE
    public GroupDTO createNewGroup(
            @NotBlank String name,
            @NotNull String description,
            @NotNull @Min(1) Integer maxMembers,
            Integer minMembers,
            Integer idealMembers,
            @NotNull UUID belongsToEventId
    ) {
        GroupEntity entityToSave = new GroupEntity();

        entityToSave.setName(name);
        entityToSave.setDescription(description);
        entityToSave.setMaxMembers(maxMembers);
        entityToSave.setMinMembers(minMembers);
        entityToSave.setIdealMembers(idealMembers);
        entityToSave.setBelongsToEvent(eventRepository.findById(belongsToEventId).orElseThrow());

        return new GroupDTO(groupRepository.save(entityToSave));
    }

    // READ
    public Optional<GroupDTO> getById(@NotNull UUID id){
        return groupRepository.findById(id).map(GroupDTO::new);
    }

    public List<GroupDTO> getAllByEventId(@NotNull UUID eventId){
        return groupRepository.findByBelongsToEvent_Id(eventId).stream().map(GroupDTO::new).toList();
    }

    // UPDATE
    public GroupDTO updateNameForId(@NotNull UUID id, @NotBlank String name){
        GroupEntity entityToUpdate = groupRepository.findById(id).orElseThrow();
        entityToUpdate.setName(name);
        return new GroupDTO(groupRepository.save(entityToUpdate));
    }

    public GroupDTO updateDescriptionForId(@NotNull UUID id, @NotBlank String description){
        GroupEntity entityToUpdate = groupRepository.findById(id).orElseThrow();
        entityToUpdate.setDescription(description);
        return new GroupDTO(groupRepository.save(entityToUpdate));
    }

    public GroupDTO updateMaxMembersForId(@NotNull UUID id, @NotNull @Min(1) Integer maxMembers){
        GroupEntity entityToUpdate = groupRepository.findById(id).orElseThrow();
        entityToUpdate.setMaxMembers(maxMembers);
        return new GroupDTO(groupRepository.save(entityToUpdate));
    }

    public GroupDTO updateMinMembersForId(@NotNull UUID id, Integer minMembers){
        GroupEntity entityToUpdate = groupRepository.findById(id).orElseThrow();
        entityToUpdate.setMinMembers(minMembers);
        return new GroupDTO(groupRepository.save(entityToUpdate));
    }

    public GroupDTO updateIdealMembersForId(@NotNull UUID id, Integer idealMembers){
        GroupEntity entityToUpdate = groupRepository.findById(id).orElseThrow();
        entityToUpdate.setIdealMembers(idealMembers);
        return new GroupDTO(groupRepository.save(entityToUpdate));
    }

    // DELETE
    // NOT INTENDED YET
}
