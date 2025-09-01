package com.trading.repository;

import com.trading.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 01-09-2025 18:56
 */
public interface OrderItemRepo extends JpaRepository<OrderItem,Long> {
}
