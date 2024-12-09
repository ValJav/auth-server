package com.inserm.user_auth_service.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.inserm.user_auth_service.security.JWTUtil;

class JWTUtilTest {
    private JWTUtil jwtUtil;
    private static final String TEST_USERNAME = "testuser";

    @BeforeEach
    void setUp() {
        jwtUtil = new JWTUtil();
    }

    @Test
    void generateToken_ShouldCreateValidToken() {
        String token = jwtUtil.generateToken(TEST_USERNAME);
        
        assertNotNull(token);
        assertTrue(!token.isEmpty());
        assertTrue(jwtUtil.validateToken(token));
    }

    @Test
    void extractUsername_ShouldReturnCorrectUsername() {
        String token = jwtUtil.generateToken(TEST_USERNAME);
        String extractedUsername = jwtUtil.extractUsername(token);
        
        assertEquals(TEST_USERNAME, extractedUsername);
    }

    @Test
    void validateToken_ShouldReturnTrueForValidToken() {
        String token = jwtUtil.generateToken(TEST_USERNAME);
        
        assertTrue(jwtUtil.validateToken(token));
    }
} 