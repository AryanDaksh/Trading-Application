package com.trading.repository;

import com.trading.entity.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 05-09-2025 22:35
 */

public interface PaymentDetailsRepo extends JpaRepository<PaymentDetails, Long> {
    PaymentDetails findByUserId(Long userId);
}
