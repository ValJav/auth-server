package com.inserm.user_auth_service.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inserm.user_auth_service.entity.User;

public interface AuthRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
