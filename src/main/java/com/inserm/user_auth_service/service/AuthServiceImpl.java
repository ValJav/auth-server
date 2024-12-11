package com.inserm.user_auth_service.service;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.inserm.user_auth_service.repository.AuthRepository;
import com.inserm.user_auth_service.dto.RegisterDTO;
import com.inserm.user_auth_service.dto.RegisterResponse;
import com.inserm.user_auth_service.entity.User;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private ModelMapper modelMapper;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public RegisterResponse register(RegisterDTO registerDTO) {
        User user = modelMapper.map(registerDTO, User.class);
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setRoles(registerDTO.getRoles());
        User save = authRepository.save(user);
        return modelMapper.map(save, RegisterResponse.class);
    }
}
