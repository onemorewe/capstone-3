package org.yearup.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.yearup.controllers.dto.ProfileDto;
import org.yearup.service.ProfileService;

import java.security.Principal;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
@CrossOrigin
public class ProfileController {

    private final ProfileService profileService;

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProfile(@RequestBody ProfileDto profileDto, Principal principal) {
        profileService.updateProfile(principal, profileDto);
    }

    @GetMapping
    public ProfileDto getProfile(Principal principal) {
        return profileService.getProfile(principal);
    }
}
