package org.yearup.controllers.dto.authentication;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RegisterUserDto {

    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    @NotEmpty
    private String confirmPassword;
    @NotEmpty(message = "Please select a role for this user.")
    private String role;
}
