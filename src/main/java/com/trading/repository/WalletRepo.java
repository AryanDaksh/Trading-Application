package com.trading.repository;

import com.trading.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 01-09-2025 01:20
 */
public interface WalletRepo extends JpaRepository<Wallet, Long> {
    Wallet findByUserId(Long userId);
}
