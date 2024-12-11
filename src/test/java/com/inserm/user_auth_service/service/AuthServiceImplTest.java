package com.inserm.user_auth_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import com.inserm.user_auth_service.repository.AuthRepository;
import com.inserm.user_auth_service.dto.RegisterDTO;
import com.inserm.user_auth_service.dto.RegisterResponse;
import com.inserm.user_auth_service.entity.User;

class AuthServiceImplTest {

    @Mock
    private AuthRepository authRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register_ShouldReturnRegisteredUser() {
        // Arrange
        RegisterDTO registerDTO = new RegisterDTO("testuser", "password123", "USER");
        User user = new User();
        user.setUsername("testuser");
        
        RegisterResponse expectedResponse = new RegisterResponse();
        expectedResponse.setUsername("testuser");

        when(modelMapper.map(registerDTO, User.class)).thenReturn(user);
        when(authRepository.save(any(User.class))).thenReturn(user);
        when(modelMapper.map(user, RegisterResponse.class)).thenReturn(expectedResponse);

        // Act
        RegisterResponse actualResponse = authService.register(registerDTO);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(expectedResponse.getUsername(), actualResponse.getUsername());
    }
} 