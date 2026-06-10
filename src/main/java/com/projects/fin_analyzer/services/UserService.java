package com.projects.fin_analyzer.services;

import com.projects.fin_analyzer.dto.*;

public interface UserService {

    UserResponse register(UserRegistrationRequest request);
}
