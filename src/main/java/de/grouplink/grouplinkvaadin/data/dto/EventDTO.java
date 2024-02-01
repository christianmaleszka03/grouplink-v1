package de.grouplink.grouplinkvaadin.data.dto;

import de.grouplink.grouplinkvaadin.data.dto.superclasses.DatedDTO;
import de.grouplink.grouplinkvaadin.data.entity.EventEntity;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EventDTO implements DatedDTO {
    private UUID id;
    private ZonedDateTime createdAt;

    private String name;
    private String description;
    private ZonedDateTime startTimestamp;
    private ZonedDateTime endTimestamp;
    private UUID ownedByUserID;
    private List<GroupDTO> groups;
    private List<VoteDTO> votes;

    public EventDTO(EventEntity entity) {
           this.id = entity.getId();
            this.createdAt = entity.getCreatedAt();
            this.name = entity.getName();
            this.description = entity.getDescription();
            this.startTimestamp = entity.getStartTimestamp();
            this.endTimestamp = entity.getEndTimestamp();
            this.ownedByUserID = entity.getOwnedByUser().getId();
            this.groups = entity.getGroups().stream().map(GroupDTO::new).toList();
            this.votes = entity.getVotes().stream().map(VoteDTO::new).toList();
    }
}
