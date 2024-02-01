package de.grouplink.grouplinkvaadin.data.repository;

import de.grouplink.grouplinkvaadin.data.entity.CantGoWithEachOtherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CantGoWithEachOtherRepository extends JpaRepository<CantGoWithEachOtherEntity, UUID> {
}
