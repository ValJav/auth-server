package com.tier3Hub.user_auth_service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.tier3Hub.user_auth_service.security.JWTFilter;
import com.tier3Hub.user_auth_service.service.UserInfoConfigManager;
import com.tier3Hub.user_auth_service.utils.AppConstants;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JWTFilter jwtFilter;

    @Autowired
    private UserInfoConfigManager userInfoConfigManager;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(request -> request
                        .requestMatchers(AppConstants.PUBLIC_URLS).permitAll()
                        .anyRequest()
                        .authenticated())
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userInfoConfigManager).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
        return auth.getAuthenticationManager();
    }
}
