package org.yearup.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.yearup.data.UserRepository;
import org.yearup.models.AppUser;
import org.yearup.service.PrincipalService;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class PrincipalServiceImpl implements PrincipalService {

    private final UserRepository userRepository;

    @Override
    public AppUser getUserByPrincipal(Principal principal) {
        String userName = principal.getName();
        return userRepository.getUserByUsername(userName);
    }
}
