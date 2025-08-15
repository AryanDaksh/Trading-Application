package com.trading.repository;

import com.trading.entity.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 12-08-2025 15:40
 */
public interface VerificationCodeRepo extends JpaRepository<VerificationCode, Long> {
    public VerificationCode findByUserId(Long userId);
}
