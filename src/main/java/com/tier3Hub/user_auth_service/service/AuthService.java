package com.tier3Hub.user_auth_service.service;

import com.tier3Hub.user_auth_service.dto.LoginResponse;
import com.tier3Hub.user_auth_service.dto.RegisterDTO;
import com.tier3Hub.user_auth_service.dto.RegisterResponse;

public interface AuthService {
    RegisterResponse register(RegisterDTO registerDTO);
}
