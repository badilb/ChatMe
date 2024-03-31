package com.chatme.chatmeapp.models.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegistrationDTO {
    @NotBlank(message = "Name must not be empty")
    private String name;

    @NotBlank(message = "Surname must not be empty")
    private String surname;

    @NotBlank(message = "Username must not be empty")
    private String username;

    @NotBlank(message = "Password must not be empty")
    private String password;
}
