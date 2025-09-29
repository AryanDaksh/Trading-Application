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
    Asset createAsset(final User user, final Coin coin, final Double quantity);
    Asset getAssetById(final Long assetId) throws Exception;
    Asset getAssetByUserIdAndId(final Long userId, final Long assetId);
    List<Asset> getUserAssets(final Long userId);
    Asset updateAsset(final Long assetId, final Double quantity) throws Exception;
    Asset findAssetByUserIdAndCoinId(final Long userId, final String coinId);
    void deleteAsset(final Long assetId);
}
