package com.projects.fin_analyzer.services;

import com.projects.fin_analyzer.dto.LoginRequest;
import com.projects.fin_analyzer.dto.LoginResponse;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {
    LoginResponse login(LoginRequest loginRequest);
}
