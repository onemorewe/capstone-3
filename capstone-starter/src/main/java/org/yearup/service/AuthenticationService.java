package org.yearup.service;

import org.yearup.controllers.dto.authentication.LoginDto;
import org.yearup.controllers.dto.authentication.LoginResponseDto;
import org.yearup.controllers.dto.authentication.RegisterUserDto;
import org.yearup.models.AppUser;

public interface AuthenticationService {
    LoginResponseDto login(LoginDto loginDto);

    AppUser register(RegisterUserDto newUser);
}
