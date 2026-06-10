package com.projects.fin_analyzer.services;

import com.projects.fin_analyzer.dto.UserRegistrationRequest;
import com.projects.fin_analyzer.dto.UserResponse;
import com.projects.fin_analyzer.entity.User;
import com.projects.fin_analyzer.mapper.UserMapper2;
import com.projects.fin_analyzer.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepo;
//    private final UserMapper userMapper;

    private final UserMapper2 userMapper2;
    private final PasswordEncoder passwordEncoder;
    public UserServiceImpl(UserRepository userRepo, UserMapper2 userMapper2, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.userMapper2 = userMapper2;
//        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponse register(UserRegistrationRequest request){
        if(userRepo.findByEmail(request.getEmail()).isPresent()){
            throw new RuntimeException("Email already exists");
        }
        User user = userMapper2.toUserEntity(request);
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        User savedUser = userRepo.save(user);
        return userMapper2.toUserResponse(savedUser);
    }
}
