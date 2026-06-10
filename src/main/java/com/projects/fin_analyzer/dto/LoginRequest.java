package com.projects.fin_analyzer.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
