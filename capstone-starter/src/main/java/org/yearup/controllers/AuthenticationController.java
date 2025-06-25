package org.yearup.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yearup.controllers.dto.authentication.LoginDto;
import org.yearup.controllers.dto.authentication.LoginResponseDto;
import org.yearup.controllers.dto.authentication.RegisterUserDto;
import org.yearup.models.AppUser;
import org.yearup.security.jwt.JWTFilter;
import org.yearup.service.AuthenticationService;

import javax.validation.Valid;

@RestController
@CrossOrigin
@PreAuthorize("permitAll()")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginDto loginDto) {

        LoginResponseDto loginResponseDto = authenticationService.login(loginDto);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + loginResponseDto.getToken());

        return new ResponseEntity<>(loginResponseDto, httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public AppUser register(@Valid @RequestBody RegisterUserDto newUser) {
        return authenticationService.register(newUser);
    }
}
