package org.yearup.service;

import org.yearup.controllers.dto.ProfileDto;
import org.yearup.models.AppUser;

import java.security.Principal;

public interface ProfileService {
    void createEmptyProfile(AppUser user);

    void updateProfile(Principal principal, ProfileDto profile);

    ProfileDto getProfile(Principal principal);
}
