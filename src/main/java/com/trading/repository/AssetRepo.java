package com.trading.repository;

import com.trading.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 01-09-2025 23:42
 */

public interface AssetRepo extends JpaRepository<Asset, Long> {
    List<Asset> findByUserId(Long userId);
    Asset findByUserIdAndCoinId(Long userId, String coinId);
}
