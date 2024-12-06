package com.tier3Hub.user_auth_service.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterResponse {
    private Long id;
    private String username;
    private String role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
