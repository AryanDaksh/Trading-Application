package com.trading.repository;

import com.trading.entity.Withdrawal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 02-09-2025 00:22
 */
public interface WithdrawalRepo extends JpaRepository<Withdrawal, Long> {
    List<Withdrawal> findByUserId(Long userId);
}
