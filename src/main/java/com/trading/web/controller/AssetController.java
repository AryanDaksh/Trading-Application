package com.trading.web.controller;

import com.trading.entity.Asset;
import com.trading.entity.User;
import com.trading.service.AssetService;
import com.trading.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 01-09-2025 23:52
 */
@RequiredArgsConstructor
@RequestMapping("/api/asset")
@RestController
public class AssetController {
    private final AssetService assetService;
    private final UserService userService;

    @GetMapping("/{assetId}")
    public ResponseEntity<Asset> getAssetById(@PathVariable Long assetId) throws Exception {
        Asset asset = assetService.getAssetById(assetId);
        return new ResponseEntity<>(asset, HttpStatus.OK);
    }

    @GetMapping("/coin/{coinId}/user")
    public ResponseEntity<Asset> getAssetByUserIdAndCoinId(@PathVariable String coinId, @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Asset asset = assetService.findAssetByUserIdAndCoinId(user.getId(), coinId);
        return new ResponseEntity<>(asset, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Asset>> getUserAssets(@RequestHeader("Authorization") String jwt) {
        User user = userService.findUserProfileByJwt(jwt);
        List<Asset> assets = assetService.getUserAssets(user.getId());
        return new ResponseEntity<>(assets, HttpStatus.OK);
    }
}
