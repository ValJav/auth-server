package com.tier3Hub.user_auth_service.Repository;

import com.tier3Hub.user_auth_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
