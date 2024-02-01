package de.grouplink.grouplinkvaadin.data.repository;

import de.grouplink.grouplinkvaadin.data.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, UUID> {
    List<GroupEntity> findByBelongsToEvent_Id(@NonNull UUID id);
}
