package com.tier3Hub.user_auth_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginDTO {
    @NotBlank(message = "Servicename is required")
    @Size(min = 3, max = 20, message = "Servicename must be between 3 and 20 characters")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;
}
