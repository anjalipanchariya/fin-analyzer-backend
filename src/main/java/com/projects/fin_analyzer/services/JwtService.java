package com.projects.fin_analyzer.services;

import com.projects.fin_analyzer.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface JwtService {

    String generateToken(User user);
    String extractEmail(String token);
    boolean isValidToken(User user, String token);

}
