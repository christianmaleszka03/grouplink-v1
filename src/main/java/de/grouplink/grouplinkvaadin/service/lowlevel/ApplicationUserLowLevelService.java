package de.grouplink.grouplinkvaadin.service.lowlevel;

import de.grouplink.grouplinkvaadin.data.dto.ApplicationUserDTO;
import de.grouplink.grouplinkvaadin.data.entity.ApplicationUserEntity;
import de.grouplink.grouplinkvaadin.data.repository.ApplicationUserRepository;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Validated
public class ApplicationUserLowLevelService {
    private final ApplicationUserRepository applicationUserRepository;

    public ApplicationUserLowLevelService(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    // CREATE
    public ApplicationUserDTO createNewApplicationUser(
            @NotBlank String firstName,
            @NotBlank String lastName,
            boolean isCompany,
            @NotBlank @Email String email,
            @NotBlank String hashedPassword
    ) {
        // NEEDED CHECKS
        if(applicationUserRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("ApplicationUser with Email already exists. This violates the unique constraint.");
        }

        ApplicationUserEntity entityToSave = new ApplicationUserEntity();
        entityToSave.setFirstName(firstName);
        entityToSave.setLastName(lastName);
        entityToSave.setCompany(isCompany);
        entityToSave.setEmail(email);
        entityToSave.setPassword(hashedPassword);
        entityToSave.setEvents(new ArrayList<>());

        return new ApplicationUserDTO(applicationUserRepository.save(entityToSave));
    }

    // READ
    public boolean doesExistByEmail(@NotBlank @Email String email){
        return applicationUserRepository.existsByEmail(email);
    }
    public List<ApplicationUserDTO> getAll(){
        return applicationUserRepository.findAll().stream()
                .map(ApplicationUserDTO::new)
                .toList();
    }

    public Optional<ApplicationUserDTO> getById(UUID id){
        return applicationUserRepository.findById(id)
                .map(ApplicationUserDTO::new);
    }

    public Optional<ApplicationUserDTO> getByEmail(String email){
        return applicationUserRepository.findByEmail(email)
                .map(ApplicationUserDTO::new);
    }

    // UPDATE
    public ApplicationUserDTO updateFirstNameForId(@NotNull UUID id, @NotBlank String newFirstName){
        ApplicationUserEntity entityToUpdate = applicationUserRepository.findById(id).orElseThrow();
        entityToUpdate.setFirstName(newFirstName);
        return new ApplicationUserDTO(applicationUserRepository.save(entityToUpdate));
    }

    public ApplicationUserDTO updateLastNameForId(@NotNull UUID id, @NotBlank String newLastName){
        ApplicationUserEntity entityToUpdate = applicationUserRepository.findById(id).orElseThrow();
        entityToUpdate.setLastName(newLastName);
        return new ApplicationUserDTO(applicationUserRepository.save(entityToUpdate));
    }

    public ApplicationUserDTO updateCompanyForId(@NotNull UUID id, boolean newIsCompany){
        ApplicationUserEntity entityToUpdate = applicationUserRepository.findById(id).orElseThrow();
        entityToUpdate.setCompany(newIsCompany);
        return new ApplicationUserDTO(applicationUserRepository.save(entityToUpdate));
    }

    public ApplicationUserDTO updateEmailForId(@NotNull UUID id, @NotBlank String newEmail){
        ApplicationUserEntity entityToUpdate = applicationUserRepository.findById(id).orElseThrow();
        entityToUpdate.setEmail(newEmail);
        return new ApplicationUserDTO(applicationUserRepository.save(entityToUpdate));
    }

    public ApplicationUserDTO updatePasswordForId(@NotNull UUID id, @NotBlank String newHashedPassword){
        ApplicationUserEntity entityToUpdate = applicationUserRepository.findById(id).orElseThrow();
        entityToUpdate.setPassword(newHashedPassword);
        return new ApplicationUserDTO(applicationUserRepository.save(entityToUpdate));
    }

    // DELETE
    // NOT INTENDED YET
}

