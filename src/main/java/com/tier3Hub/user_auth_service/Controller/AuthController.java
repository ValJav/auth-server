package com.tier3Hub.user_auth_service.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tier3Hub.user_auth_service.dto.LoginDTO;
import com.tier3Hub.user_auth_service.dto.LoginResponse;
import com.tier3Hub.user_auth_service.dto.RegisterDTO;
import com.tier3Hub.user_auth_service.security.JWTUtil;
import com.tier3Hub.user_auth_service.service.AuthService;
import com.tier3Hub.user_auth_service.service.UserInfoConfigManager;
import com.tier3Hub.user_auth_service.utils.ResponseHandler;

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
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("Incorrect servicename or password", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/validate")
    public ResponseEntity<Object> validate(@RequestHeader("Authorization") String token) {
        String username = null;
        String jwt = null;
        System.out.println("Token : " + token);
        if (token != null && token.startsWith("Bearer ")) {
            jwt = token.substring(7);
            username = jwtUtil.extractUsername(jwt);
            System.out.println("Username : " + username);
        }
        if (username != null) {
            UserDetails userDetails = userInfoConfigManager.loadUserByUsername(username);
            System.out.println("UserDetails : " + userDetails);
            if (Boolean.TRUE.equals(jwtUtil.validateToken(jwt))) {
                System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                System.out.println("Auth : " + auth);
                return ResponseHandler.generateResponse("Token is valid", HttpStatus.OK, auth);
            }
        }
        System.out.println("Invalid token");
        return ResponseHandler.generateResponse("Invalid token", HttpStatus.UNAUTHORIZED, null);
    }
}
