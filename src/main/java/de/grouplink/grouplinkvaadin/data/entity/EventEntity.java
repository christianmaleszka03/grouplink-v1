package de.grouplink.grouplinkvaadin.data.entity;

import de.grouplink.grouplinkvaadin.data.entity.superclasses.DatedEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "event_entity")
public class EventEntity extends DatedEntity {
    @Column(name = "name")
    @NotBlank
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    @NotNull
    private String description = "";

    @Column(name = "start_timestamp", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @NotNull
    private ZonedDateTime startTimestamp;

    @Column(name = "end_timestamp", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private ZonedDateTime endTimestamp;

    @ManyToOne
    @JoinColumn(name = "owned_by_user_id")
    private ApplicationUserEntity ownedByUser;

    @OneToMany(mappedBy = "belongsToEvent", fetch = FetchType.EAGER)
    private List<GroupEntity> groups = new ArrayList<>();

    @OneToMany(mappedBy = "belongsToEvent", fetch = FetchType.EAGER)
    private List<VoteEntity> votes = new ArrayList<>();
}
