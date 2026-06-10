package com.projects.fin_analyzer.mapper;

import com.projects.fin_analyzer.dto.UserRegistrationRequest;
import com.projects.fin_analyzer.dto.UserResponse;
import com.projects.fin_analyzer.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "passwordHash", source = "password")
    User toEntity(UserRegistrationRequest request);

    UserResponse toResponse(User user);
}