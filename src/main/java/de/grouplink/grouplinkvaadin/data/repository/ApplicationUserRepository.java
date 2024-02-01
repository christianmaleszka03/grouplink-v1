package de.grouplink.grouplinkvaadin.data.repository;

import de.grouplink.grouplinkvaadin.data.entity.ApplicationUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUserEntity, UUID> {
    Optional<ApplicationUserEntity> findByEmail(@NonNull String email);
    boolean existsByEmail(@NonNull String email);
}
