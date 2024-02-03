package de.grouplink.grouplinkvaadin.security;

import com.vaadin.flow.spring.security.AuthenticationContext;
import de.grouplink.grouplinkvaadin.data.dto.ApplicationUserDTO;
import de.grouplink.grouplinkvaadin.service.lowlevel.ApplicationUserLowLevelService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticatedUserHelper {
    private final ApplicationUserLowLevelService applicationUserLowLevelService;
    private final AuthenticationContext authenticationContext;

    public AuthenticatedUserHelper(ApplicationUserLowLevelService applicationUserLowLevelService, AuthenticationContext authenticationContext) {
        this.applicationUserLowLevelService = applicationUserLowLevelService;
        this.authenticationContext = authenticationContext;
    }

    public Optional<ApplicationUserDTO> get() {
        Optional<UserDetails> userDetailsOptional = authenticationContext.getAuthenticatedUser(UserDetails.class);
        if(userDetailsOptional.isEmpty()) {
            return Optional.empty();
        }

        return applicationUserLowLevelService.getByEmail(userDetailsOptional.get().getUsername());
    }

    public void logout() {
        authenticationContext.logout();
    }

}
