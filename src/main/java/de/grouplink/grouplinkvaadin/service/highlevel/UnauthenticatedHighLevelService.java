package de.grouplink.grouplinkvaadin.service.highlevel;

import de.grouplink.grouplinkvaadin.data.dto.ApplicationUserDTO;
import de.grouplink.grouplinkvaadin.service.lowlevel.ApplicationUserLowLevelService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@Slf4j
public class UnauthenticatedHighLevelService {
    // SERVICES
    private final ApplicationUserLowLevelService applicationUserLowLevelService;

    // UTILS
    private final PasswordEncoder passwordEncoder;

    public UnauthenticatedHighLevelService(
            ApplicationUserLowLevelService applicationUserLowLevelService,
            PasswordEncoder passwordEncoder
    ) {
        this.applicationUserLowLevelService = applicationUserLowLevelService;
        this.passwordEncoder = passwordEncoder;
    }

    // APPLICATION USER SECTION
    public ApplicationUserDTO registerNewUser(
            @NotBlank String firstName,
            @NotBlank String lastName,
            @NotBlank @Email String email,
            @NotBlank String password,
            boolean isCompany
    ) {
        return applicationUserLowLevelService.createNewApplicationUser(
                firstName,
                lastName,
                isCompany,
                email,
                passwordEncoder.encode(password)
        );
    }
}
