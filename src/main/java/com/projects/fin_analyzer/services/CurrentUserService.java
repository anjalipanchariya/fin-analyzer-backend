package com.projects.fin_analyzer.services;

import com.projects.fin_analyzer.entity.User;
import com.projects.fin_analyzer.repository.UserRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {
    private final UserRepository userRepository;

    public CurrentUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (authentication == null) {
            throw new RuntimeException("User not authenticated");
        }

        String email = authentication.getName();

        return userRepository
                .findByEmail(email)
                .orElseThrow(
                        () -> new RuntimeException("User not found")
                );
    }
}
