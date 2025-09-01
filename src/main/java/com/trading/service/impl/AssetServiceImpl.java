package com.trading.service.impl;

import com.trading.entity.Asset;
import com.trading.entity.Coin;
import com.trading.entity.User;
import com.trading.repository.AssetRepo;
import com.trading.service.AssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 01-09-2025 23:39
 */
@RequiredArgsConstructor
@Service
public class AssetServiceImpl implements AssetService {
    private final AssetRepo assetRepo;

    @Override
    public Asset createAsset(User user, Coin coin, Double quantity) {
        Asset asset = new Asset();
        asset.setUser(user);
        asset.setCoin(coin);
        asset.setQuantity(quantity);
        asset.setBuyingPrice(coin.getCurrentPrice());
        return assetRepo.save(asset);
    }

    @Override
    public Asset getAssetById(Long assetId) throws Exception {
        return assetRepo.findById(assetId).orElseThrow(() -> new Exception("Asset not found!"));
    }

    @Override
    public Asset getAssetByUserIdAndId(Long userId, Long assetId) {
        return null;
    }

    @Override
    public List<Asset> getUserAssets(Long userId) {
        return assetRepo.findByUserId(userId);
    }

    @Override
    public Asset updateAsset(Long assetId, Double quantity) throws Exception {
        Asset oldAsset = getAssetById(assetId);
        oldAsset.setQuantity(quantity + oldAsset.getQuantity());
        return assetRepo.save(oldAsset);
    }

    @Override
    public Asset findAssetByUserIdAndCoinId(Long userId, String coinId) {
        return assetRepo.findByUserIdAndCoinId(userId, coinId);
    }

    @Override
    public void deleteAsset(Long assetId) {
        assetRepo.deleteById(assetId);
    }
}
