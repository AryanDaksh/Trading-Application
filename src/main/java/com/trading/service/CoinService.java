package com.trading.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.trading.entity.Coin;

import java.util.List;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 15-08-2025 13:27
 */
public interface CoinService {
    List<Coin> getCoinList(final int page);
    String getMarketChart(final String coinId, final int days);
    String getCoinDetails(final String coinId) throws JsonProcessingException;
    Coin findCoinById(final String coinId) throws Exception;
    String searchCoin(final String keyword);
    String getTop50CoinByMarketCap();
    String getTrendingCoins();
}
