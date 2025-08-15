package com.trading.repository;

import com.trading.entity.ForgotPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 12-08-2025 20:53
 */

public interface ForgotPasswordRepo extends JpaRepository<ForgotPasswordToken, String> {
    ForgotPasswordToken findByUserId(Long userId);
}
