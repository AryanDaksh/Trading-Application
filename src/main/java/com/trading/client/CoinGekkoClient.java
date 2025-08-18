package com.trading.client;

import com.trading.entity.Coin;

import java.util.List;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 15-08-2025 13:50
 */
public interface CoinGekkoClient {
    List<Coin> getCoinList();
    String getMarketChart(String coinId, int days);

    String getCoinDetails(String coinId);
}
