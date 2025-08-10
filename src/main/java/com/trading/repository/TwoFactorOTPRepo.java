package com.trading.repository;

import com.trading.entity.TwoFactorOTP;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 09-08-2025 17:57
 */
public interface TwoFactorOTPRepo extends JpaRepository<TwoFactorOTP, String> {
    TwoFactorOTP findByUserId(Long userId);
}
