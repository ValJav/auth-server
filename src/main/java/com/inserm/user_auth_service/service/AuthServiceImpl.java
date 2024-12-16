package com.inserm.user_auth_service.service;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.inserm.user_auth_service.dto.RegisterDTO;
import com.inserm.user_auth_service.dto.RegisterResponse;
import com.inserm.user_auth_service.entity.User;
import com.inserm.user_auth_service.repository.AuthRepository;

@Service
public class AuthServiceImpl implements AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private ModelMapper modelMapper;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public RegisterResponse register(RegisterDTO registerDTO) {
        logger.info("Starting user registration process for username: {}", registerDTO.getUsername());
        try {
            logger.debug("Mapping RegisterDTO to User entity");
            User user = modelMapper.map(registerDTO, User.class);
            user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
            user.setCreatedAt(LocalDateTime.now());
            user.setRoles(registerDTO.getRoles());
            
            logger.debug("Saving user to database");
            User savedUser = authRepository.save(user);
            
            logger.info("User successfully registered: {}", savedUser.getUsername());
            return modelMapper.map(savedUser, RegisterResponse.class);
        } catch (Exception e) {
            logger.error("Error during user registration: {}", e.getMessage(), e);
            throw e;
        }
    }
}
