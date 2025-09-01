package com.trading.service;

import com.trading.entity.Asset;
import com.trading.entity.Coin;
import com.trading.entity.User;

import java.util.List;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 01-09-2025 23:39
 */
public interface AssetService {
    Asset createAsset(User user, Coin coin, Double quantity);
    Asset getAssetById(Long assetId) throws Exception;
    Asset getAssetByUserIdAndId(Long userId, Long assetId);
    List<Asset> getUserAssets(Long userId);
    Asset updateAsset(Long assetId, Double quantity) throws Exception;
    Asset findAssetByUserIdAndCoinId(Long userId, String coinId);
    void deleteAsset(Long assetId);
}
