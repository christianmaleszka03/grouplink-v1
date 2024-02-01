package de.grouplink.grouplinkvaadin.data.entity;

import de.grouplink.grouplinkvaadin.data.entity.superclasses.AbstractEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "cant_go_with_each_other_entity")
public class CantGoWithEachOtherEntity extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "issuing_vote_id")
    private VoteEntity issuingVote;

    @Column(name = "text", columnDefinition = "TEXT")
    @NotBlank
    private String text;

    @ManyToOne
    @JoinColumn(name = "vote_pointed_to_id")
    private VoteEntity votePointedTo;
}
