package de.grouplink.grouplinkvaadin.data.repository;

import de.grouplink.grouplinkvaadin.data.entity.VoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VoteRepository extends JpaRepository<VoteEntity, UUID> {
    List<VoteEntity> findByBelongsToEvent_Id(@NonNull UUID id);
}
