package org.yearup.service;

import org.yearup.models.AppUser;

import java.security.Principal;

public interface PrincipalService {
    AppUser getUserByPrincipal(Principal principal);
}
