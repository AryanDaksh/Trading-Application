package com.trading.repository;

import com.trading.entity.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 05-09-2025 22:49
 */
public interface PaymentOrderRepo extends JpaRepository<PaymentOrder, Long> {
}
