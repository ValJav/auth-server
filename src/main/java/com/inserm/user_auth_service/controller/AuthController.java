package com.inserm.user_auth_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inserm.user_auth_service.dto.LoginDTO;
import com.inserm.user_auth_service.dto.LoginResponse;
import com.inserm.user_auth_service.dto.RegisterDTO;
import com.inserm.user_auth_service.exception.AuthException;
import com.inserm.user_auth_service.security.JWTUtil;
import com.inserm.user_auth_service.service.AuthService;
import com.inserm.user_auth_service.service.UserInfoConfigManager;
import com.inserm.user_auth_service.utils.ResponseHandler;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    AuthService authService;

    @Autowired
    AuthenticationManager authenticationManager;
    
    @Autowired
    private UserInfoConfigManager userInfoConfigManager;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody RegisterDTO registerDTO) {
        return ResponseHandler.generateResponse("User registered successfully", HttpStatus.OK, authService.register(registerDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginDTO loginDTO) {
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
            UserDetails userDetails = userInfoConfigManager.loadUserByUsername(loginDTO.getUsername());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            LoginResponse loginResponse = LoginResponse
                    .builder()
                    .accessToken(jwt)
                    .build();
            return ResponseHandler.generateResponse("Service logged in successfully", HttpStatus.OK, loginResponse);
        } catch (BadCredentialsException e) {
            throw new AuthException("Invalid username or password");
        }
    }

    @PostMapping("/validate")
    public ResponseEntity<Object> validate(@RequestHeader("Authorization") String token) {
        try {
            if (token == null || !token.startsWith("Bearer ")) {
                throw new AuthException("Invalid token format");
            }

            String jwt = token.substring(7);
            String username = jwtUtil.extractUsername(jwt);

            if (username == null) {
                throw new AuthException("Invalid token");
            }

            UserDetails userDetails = userInfoConfigManager.loadUserByUsername(username);
            if (Boolean.TRUE.equals(jwtUtil.validateToken(jwt))) {
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
                return ResponseHandler.generateResponse("Token is valid", HttpStatus.OK, userDetails);
            }
            throw new AuthException("Invalid token");
        } catch (ExpiredJwtException e) {
            throw new AuthException("Token has expired");
        } catch (SignatureException e) {
            throw new AuthException("Invalid token signature");
        }
    }
}
