package com.chatme.chatmeapp.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginDTO {
    @NotBlank(message = "Username must not be empty")
    @Size(min = 4, max = 35, message = "The length of the username must be at least 4 characters and no more than 35 characters")
    private String username;

    @NotBlank(message = "Password must not be empty")
    private String password;
}
