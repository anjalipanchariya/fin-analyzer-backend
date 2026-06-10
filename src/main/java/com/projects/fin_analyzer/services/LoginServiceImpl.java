package com.projects.fin_analyzer.services;

import com.projects.fin_analyzer.dto.LoginRequest;
import com.projects.fin_analyzer.dto.LoginResponse;
import com.projects.fin_analyzer.entity.User;
import com.projects.fin_analyzer.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public LoginServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {

        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found. Please register"));

        if(!passwordEncoder.matches(
                loginRequest.getPassword(),user.getPasswordHash()
        )){
            throw new RuntimeException("Invalid credentials");
        }
        String token = jwtService.generateToken(user);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);

        return loginResponse;
    }
}
