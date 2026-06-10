package com.projects.fin_analyzer.mapper;

import com.projects.fin_analyzer.dto.UserRegistrationRequest;
import com.projects.fin_analyzer.dto.UserResponse;
import com.projects.fin_analyzer.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper2 {

    public User toUserEntity(UserRegistrationRequest request){
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPasswordHash(request.getPassword());

        return user;
    }

    public UserResponse toUserResponse(User user){
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setEmail(user.getEmail());
        userResponse.setName(user.getName());
        return userResponse;
    }
}
