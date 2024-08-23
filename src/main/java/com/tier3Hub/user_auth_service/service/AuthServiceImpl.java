package com.tier3Hub.user_auth_service.service;

import com.tier3Hub.user_auth_service.Repository.AuthRepository;
import com.tier3Hub.user_auth_service.dto.RegisterDTO;
import com.tier3Hub.user_auth_service.dto.RegisterResponse;
import com.tier3Hub.user_auth_service.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public RegisterResponse register(RegisterDTO registerDTO) {
        User user = modelMapper.map(registerDTO, User.class);
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        User save = authRepository.save(user);
        return modelMapper.map(save, RegisterResponse.class);
    }
}
