package de.grouplink.grouplinkvaadin.data.dto;

import de.grouplink.grouplinkvaadin.data.dto.superclasses.DatedDTO;
import de.grouplink.grouplinkvaadin.data.entity.GroupEntity;
import de.grouplink.grouplinkvaadin.data.entity.VoteEntity;
import de.grouplink.grouplinkvaadin.data.enums.Gender;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VoteDTO implements DatedDTO {
    private UUID id;
    private ZonedDateTime createdAt;

    private String firstName;
    private String lastName;
    private Gender gender;
    private UUID belongsToEventID;
    private GroupDTO assignedToGroup;
    private Map<GroupDTO, Integer> groupPreferences;
    private List<CantGoWithEachOtherDTO> cantGoWithEachOthers;

    public VoteDTO(VoteEntity entity) {
        this.id = entity.getId();
        this.createdAt = entity.getCreatedAt();
        this.firstName = entity.getFirstName();
        this.lastName = entity.getLastName();
        this.gender = entity.getGender();
        this.belongsToEventID = entity.getBelongsToEvent().getId();
        this.assignedToGroup = new GroupDTO(entity.getAssignedToGroup());
        this.cantGoWithEachOthers = entity.getCantGoWithEachOthers().stream().map(CantGoWithEachOtherDTO::new).toList();
        Map<GroupDTO, Integer> groupPreferencesMap = new HashMap<>();
        for (Map.Entry<GroupEntity, Integer> entry : entity.getGroupPreferences().entrySet()) {
            groupPreferencesMap.put(new GroupDTO(entry.getKey()), entry.getValue());
        }
        this.groupPreferences = groupPreferencesMap;
    }
}
