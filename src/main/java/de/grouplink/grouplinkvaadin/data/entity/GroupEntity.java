package de.grouplink.grouplinkvaadin.data.entity;

import de.grouplink.grouplinkvaadin.data.entity.superclasses.AbstractEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "group_entity")
public class GroupEntity extends AbstractEntity {
    @Column(name = "name")
    @NotBlank
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    @NotNull
    private String description = "";

    @Column(name = "max_members")
    @NotNull
    @Min(1)
    private int maxMembers;

    @Column(name = "min_members")
    @Min(0)
    private int minMembers;

    @Column(name = "ideal_members")
    @Min(0)
    private int idealMembers;

    @ManyToOne
    @JoinColumn(name = "belongs_to_event_id")
    private EventEntity belongsToEvent;
}
