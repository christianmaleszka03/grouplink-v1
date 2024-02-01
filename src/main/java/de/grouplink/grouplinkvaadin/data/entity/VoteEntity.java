package de.grouplink.grouplinkvaadin.data.entity;

import de.grouplink.grouplinkvaadin.data.entity.superclasses.DatedEntity;
import de.grouplink.grouplinkvaadin.data.enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
@Entity
@Table(name = "vote_entity")
public class VoteEntity extends DatedEntity {
    @Column(name = "first_name")
    @NotBlank
    private String firstName;

    @Column(name = "last_name")
    @NotBlank
    private String lastName;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    @NotNull
    private Gender gender;

    @ManyToOne
    @JoinColumn(name = "belongs_to_event_id")
    private EventEntity belongsToEvent;

    @ManyToOne
    @JoinColumn(name = "assigned_to_group_id")
    private GroupEntity assignedToGroup;

    @OneToMany(mappedBy = "issuingVote")
    private List<CantGoWithEachOtherEntity> cantGoWithEachOthers = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "vote_group_preferences", joinColumns = @JoinColumn(name = "vote_id"))
    @MapKeyJoinColumn(name = "group_id")
    @Column(name = "preference_order")
    private Map<GroupEntity, Integer> groupPreferences = new HashMap<>();
}
