package de.grouplink.grouplinkvaadin.data.entity.superclasses;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;

import java.time.ZonedDateTime;

@MappedSuperclass
public abstract class DatedEntity extends AbstractEntity {
    @Column(name = "created_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @NotNull
    private ZonedDateTime createdAt = ZonedDateTime.now();

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}
