package com.trading.repository;

import com.trading.entity.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 15-08-2025 13:33
 */
public interface CoinRepo extends JpaRepository<Coin, String> {
}
