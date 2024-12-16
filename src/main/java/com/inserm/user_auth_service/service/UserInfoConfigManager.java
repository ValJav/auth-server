package com.inserm.user_auth_service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.inserm.user_auth_service.entity.User;
import com.inserm.user_auth_service.repository.AuthRepository;

@Service
public class UserInfoConfigManager implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserInfoConfigManager.class);

    @Autowired
    private AuthRepository authRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("Loading user details for username: {}", username);
        try {
            User user = authRepository.findByUsername(username);
            if (user == null) {
                logger.warn("User not found: {}", username);
                throw new UsernameNotFoundException("User not found with username: " + username);
            }
            logger.info("User details loaded successfully for: {}", username);
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles(user.getRoles().toArray(new String[0]))
                    .build();
        } catch (Exception e) {
            logger.error("Error loading user details: {}", e.getMessage(), e);
            throw e;
        }
    }
}
