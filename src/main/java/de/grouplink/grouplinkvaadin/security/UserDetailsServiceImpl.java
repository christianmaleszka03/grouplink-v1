package de.grouplink.grouplinkvaadin.security;

import de.grouplink.grouplinkvaadin.data.dto.ApplicationUserDTO;
import de.grouplink.grouplinkvaadin.service.lowlevel.ApplicationUserLowLevelService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final ApplicationUserLowLevelService applicationUserLowLevelService;

    public UserDetailsServiceImpl(ApplicationUserLowLevelService applicationUserLowLevelService) {
        this.applicationUserLowLevelService = applicationUserLowLevelService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        Optional<ApplicationUserDTO> applicationUserOptional = applicationUserLowLevelService.getByEmail(email);
        if(applicationUserOptional.isEmpty()) {
            throw new UsernameNotFoundException("No user found with email: " + email);
        }

        ApplicationUserDTO applicationUser = applicationUserOptional.get();
        return new org.springframework.security.core.userdetails.User(
                applicationUser.getEmail(),
                applicationUser.getPassword(),
                getAuthorities(applicationUser)
        );
    }

    private static List<GrantedAuthority> getAuthorities(ApplicationUserDTO applicationUser) {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }
}


