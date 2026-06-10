package com.projects.fin_analyzer.controller;

import com.projects.fin_analyzer.dto.LoginRequest;
import com.projects.fin_analyzer.dto.LoginResponse;
import com.projects.fin_analyzer.services.LoginService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class LoginController {
    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest){
        return loginService.login(loginRequest);
    }
}
