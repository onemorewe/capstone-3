package org.yearup.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.controllers.dto.ProfileDto;
import org.yearup.data.mysql.ProfileRepository;
import org.yearup.mapper.ProfileMapper;
import org.yearup.models.AppUser;
import org.yearup.models.Profile;
import org.yearup.service.PrincipalService;
import org.yearup.service.ProfileService;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final PrincipalService principalService;
    private final ProfileMapper profileMapper;

    @Override
    public void createEmptyProfile(AppUser user) {
        Profile profile = new Profile();
        profile.setUser(user);
        profileRepository.save(profile);
    }

    @Override
    public void updateProfile(Principal principal, ProfileDto profile) {
        AppUser user = principalService.getUserByPrincipal(principal);
        Profile existingProfile = profileRepository.findByUser(user);
        if (existingProfile == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found for user: " + user.getUsername());
        }
        profileMapper.updateProfileFromDto(profile, existingProfile);

        profileRepository.save(existingProfile);
    }

    @Override
    public ProfileDto getProfile(Principal principal) {
        AppUser user = principalService.getUserByPrincipal(principal);
        Profile profile = profileRepository.findByUser(user);
        if (profile == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found for user: " + user.getUsername());
        }
        return profileMapper.toDto(profile);
    }
}
