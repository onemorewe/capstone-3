package org.yearup.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.controllers.dto.authentication.LoginDto;
import org.yearup.controllers.dto.authentication.LoginResponseDto;
import org.yearup.controllers.dto.authentication.RegisterUserDto;
import org.yearup.data.mysql.ProfileRepository;
import org.yearup.data.mysql.UserRepository;
import org.yearup.models.AppUser;
import org.yearup.models.Profile;
import org.yearup.security.jwt.TokenProvider;
import org.yearup.service.AuthenticationService;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;


    @Override
    public LoginResponseDto login(LoginDto loginDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication, false);

        AppUser user = userRepository.getByUsername(loginDto.getUsername().toLowerCase());
        if (user == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return new LoginResponseDto(jwt, user);

    }

    @Override
    @Transactional
    public AppUser register(RegisterUserDto newUser) {
        boolean exists = userRepository.existsByUsername(newUser.getUsername());
        if (exists) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User Already Exists.");
        }

        // hash password
        String hashedPassword = new BCryptPasswordEncoder().encode(newUser.getPassword());

        // create user
        AppUser appUser = userRepository.save(new AppUser(0, newUser.getUsername(), hashedPassword, newUser.getRole()));

        // create profile
        if (newUser.getRole().equals("USER")) {
            System.out.println("Creating profile for user: " + appUser.getUsername());
            Profile profile = new Profile();
            profile.setUser(appUser);
            profileRepository.save(profile);
        }

        return appUser;
    }
}
