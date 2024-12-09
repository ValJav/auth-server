package com.inserm.user_auth_service.controller;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inserm.user_auth_service.dto.LoginDTO;
import com.inserm.user_auth_service.dto.RegisterDTO;
import com.inserm.user_auth_service.dto.RegisterResponse;
import com.inserm.user_auth_service.security.JWTUtil;
import com.inserm.user_auth_service.service.AuthService;
import com.inserm.user_auth_service.service.UserInfoConfigManager;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @MockBean
    private JWTUtil jwtUtil;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private UserInfoConfigManager userInfoConfigManager;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void register_ShouldReturnSuccess() throws Exception {
        RegisterDTO registerDTO = new RegisterDTO("testuser", "password123", "USER");
        RegisterResponse response = new RegisterResponse();
        response.setUsername("testuser");

        when(authService.register(any(RegisterDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void login_ShouldReturnToken() throws Exception {
        LoginDTO loginDTO = new LoginDTO("testuser", "password123");
        Authentication auth = new UsernamePasswordAuthenticationToken("testuser", "password123");
        UserDetails userDetails = User.builder()
                .username("testuser")
                .password("password123")
                .authorities(new ArrayList<>())
                .build();

        when(authenticationManager.authenticate(any())).thenReturn(auth);
        when(userInfoConfigManager.loadUserByUsername("testuser")).thenReturn(userDetails);
        when(jwtUtil.generateToken("testuser")).thenReturn("test.jwt.token");

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isOk());
    }
} 