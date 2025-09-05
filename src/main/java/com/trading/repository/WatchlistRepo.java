package com.trading.repository;

import com.trading.entity.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 03-09-2025 23:31
 */
public interface WatchlistRepo extends JpaRepository<Watchlist, Long> {
    Watchlist findByUserId(Long userId);
}
