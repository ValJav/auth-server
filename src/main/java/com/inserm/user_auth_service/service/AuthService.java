package com.inserm.user_auth_service.service;

import com.inserm.user_auth_service.dto.RegisterDTO;
import com.inserm.user_auth_service.dto.RegisterResponse;

public interface AuthService {
    RegisterResponse register(RegisterDTO registerDTO);
}
