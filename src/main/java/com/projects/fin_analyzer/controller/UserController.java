package com.projects.fin_analyzer.controller;

import com.projects.fin_analyzer.dto.UserRegistrationRequest;
import com.projects.fin_analyzer.dto.UserResponse;
import com.projects.fin_analyzer.services.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserResponse register( @Valid @RequestBody UserRegistrationRequest request){
        return userService.register(request);
    }
}
