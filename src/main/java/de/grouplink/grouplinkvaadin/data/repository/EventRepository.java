package de.grouplink.grouplinkvaadin.data.repository;

import de.grouplink.grouplinkvaadin.data.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, UUID> {
    List<EventEntity> findByOwnedByUser_Id(@NonNull UUID id);
}
