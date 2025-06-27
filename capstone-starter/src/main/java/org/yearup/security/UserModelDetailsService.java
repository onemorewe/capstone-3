package org.yearup.security;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.yearup.models.AppUser;
import org.yearup.repository.UserRepository;

import java.util.List;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
@RequiredArgsConstructor
public class UserModelDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(UserModelDetailsService.class);

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authenticating user '{}'", login);
        String lowercaseLogin = login.toLowerCase();
        return createSpringSecurityUser(lowercaseLogin, userRepository.getByUsername(lowercaseLogin));
    }

    private User createSpringSecurityUser(String lowercaseLogin, AppUser appUser) {
        if (!appUser.isActivated()) {
            throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
        }
        List<GrantedAuthority> grantedAuthorities = List.of(new SimpleGrantedAuthority(appUser.getRole()));
        return new User(appUser.getUsername(),
                appUser.getPassword(),
                grantedAuthorities);
    }
}

