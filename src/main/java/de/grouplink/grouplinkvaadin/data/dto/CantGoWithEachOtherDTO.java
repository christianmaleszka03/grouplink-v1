package de.grouplink.grouplinkvaadin.data.dto;

import de.grouplink.grouplinkvaadin.data.dto.superclasses.AbstractDTO;
import de.grouplink.grouplinkvaadin.data.entity.CantGoWithEachOtherEntity;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CantGoWithEachOtherDTO implements AbstractDTO {
    private UUID id;

    private UUID issuingVoteID;
    private String text;
    private UUID votePointedToId;

    public CantGoWithEachOtherDTO(CantGoWithEachOtherEntity entity) {
        this.id = entity.getId();
        this.issuingVoteID = entity.getIssuingVote().getId();
        this.text = entity.getText();
        this.votePointedToId = entity.getVotePointedTo().getId();
    }
}
