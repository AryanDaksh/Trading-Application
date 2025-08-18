package com.trading.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trading.client.CoinGekkoClient;
import com.trading.entity.Coin;
import com.trading.repository.CoinRepo;
import com.trading.service.CoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 15-08-2025 13:28
 */
@Service
@RequiredArgsConstructor
public class CoinServiceImpl implements CoinService {
    private final CoinRepo coinRepo;
    private final CoinGekkoClient coinGekkoClient;
    private final ObjectMapper objectMapper;

    @Override
    public List<Coin> getCoinList(int page) {
        return coinGekkoClient.getCoinList();
    }

    @Override
    public String getMarketChart(String coinId, int days) {
        return coinGekkoClient.getMarketChart(coinId, days);
    }

    @Override
    public String getCoinDetails(String coinId) throws JsonProcessingException {
        String coinDetails = coinGekkoClient.getCoinDetails(coinId);
        Coin coin = new Coin();
        //coin.setId(coinId);
        Coin coin2 = objectMapper.readValue(coinDetails, Coin.class);
        coinRepo.save(coin2);
        return coinDetails;
    }

    @Override
    public Coin findCoinById(String coinId) {
        return null;
    }

    @Override
    public String searchCoin(String keyword) {
        return "";
    }

    @Override
    public String getTop50CoinByMarketCap(int page) {
        return "";
    }

    @Override
    public String getTradingCoins(int page) {
        return "";
    }
}
