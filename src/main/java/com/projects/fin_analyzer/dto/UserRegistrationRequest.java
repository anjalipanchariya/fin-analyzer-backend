package com.projects.fin_analyzer.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
public class UserRegistrationRequest {
    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 8)
    private String password;
}
