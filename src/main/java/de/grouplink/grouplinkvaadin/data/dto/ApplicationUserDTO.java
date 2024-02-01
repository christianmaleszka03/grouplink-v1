package de.grouplink.grouplinkvaadin.data.dto;

import de.grouplink.grouplinkvaadin.data.dto.superclasses.DatedDTO;
import de.grouplink.grouplinkvaadin.data.entity.ApplicationUserEntity;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ApplicationUserDTO implements DatedDTO {
    private UUID id;
    private ZonedDateTime createdAt;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean isCompany;

    public ApplicationUserDTO(ApplicationUserEntity entity) {
        this.id = entity.getId();
        this.createdAt = entity.getCreatedAt();
        this.firstName = entity.getFirstName();
        this.lastName = entity.getLastName();
        this.email = entity.getEmail();
        this.password = entity.getPassword();
        this.isCompany = entity.isCompany();
    }
}
