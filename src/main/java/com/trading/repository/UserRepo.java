package com.trading.repository;

import com.trading.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 08-08-2025 01:24
 */
public interface UserRepo extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
