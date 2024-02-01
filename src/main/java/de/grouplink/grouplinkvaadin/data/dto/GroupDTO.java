package de.grouplink.grouplinkvaadin.data.dto;

import de.grouplink.grouplinkvaadin.data.dto.superclasses.AbstractDTO;
import de.grouplink.grouplinkvaadin.data.entity.GroupEntity;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GroupDTO implements AbstractDTO {
    private UUID id;

    private String name;
    private String description;
    private int maxMembers;
    private int minMembers;
    private int idealMembers;
    private UUID belongsToEventID;

    public GroupDTO(GroupEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.maxMembers = entity.getMaxMembers();
        this.minMembers = entity.getMinMembers();
        this.idealMembers = entity.getIdealMembers();
        this.belongsToEventID = entity.getBelongsToEvent().getId();
    }
}
