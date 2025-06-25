package org.yearup.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.yearup.controllers.dto.UpdateProfileDto;
import org.yearup.service.ProfileService;

import java.security.Principal;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProfile(@RequestBody UpdateProfileDto updateProfileDto, Principal principal) {
        profileService.updateProfile(principal, updateProfileDto);
    }
}
