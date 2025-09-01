package com.trading.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trading.client.CoinGekkoClient;
import com.trading.entity.Coin;
import com.trading.repository.CoinRepo;
import com.trading.service.CoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
        return coinGekkoClient.getCoinList(page);
    }

    @Override
    public String getMarketChart(String coinId, int days) {
        return coinGekkoClient.getMarketChart(coinId, days);
    }

    @Override
    public String getCoinDetails(String coinId) throws JsonProcessingException {
        String coinDetails = coinGekkoClient.getCoinDetails(coinId);
        JsonNode jsonNode = objectMapper.readTree(coinDetails);

        Coin coin = new Coin();
        coin.setId(jsonNode.get("id").asText(null));
        coin.setName(jsonNode.get("name").asText(null));
        coin.setSymbol(jsonNode.get("symbol").asText(null));
        coin.setImage(jsonNode.get("image").get("large").asText(null));
        coin.setMarketCapRank(jsonNode.get("market_cap_rank").asInt(0));

        JsonNode marketData = jsonNode.get("market_data");
        coin.setCurrentPrice(marketData.get("current_price").get("usd").asDouble(0));
        coin.setMarketCap(marketData.get("market_cap").get("usd").asLong(0));
        coin.setMarketCapRank(marketData.get("market_cap_rank").asInt(0));
        coin.setFullyDilutedValuation(marketData.get("fully_diluted_valuation").get("usd").asLong(0));
        coin.setTotalVolume(marketData.get("total_volume").get("usd").asLong(0));
        coin.setHigh24h(marketData.get("high_24h").get("usd").asDouble(0));
        coin.setLow24h(marketData.get("low_24h").get("usd").asDouble(0));
        coin.setPriceChange24h(marketData.get("price_change_24h").asDouble(0));
        coin.setPriceChangePercentage24h(marketData.get("price_change_percentage_24h").asDouble(0));
        coin.setMarketCapChange24h(marketData.get("market_cap_change_24h").asLong(0));
        coin.setMarketCapChangePercentage24h(marketData.get("market_cap_change_percentage_24h").asDouble(0));
        coin.setCirculatingSupply(marketData.get("circulating_supply").asLong(0));
        coin.setTotalSupply(marketData.get("total_supply").asLong(0L));
        coin.setAth(marketData.get("ath").get("usd").asDouble(0));
        coin.setAthChangePercentage(marketData.get("ath_change_percentage").get("usd").asDouble(0));
        coin.setAthDate(Date.from(OffsetDateTime.parse(marketData.get("ath_date").get("usd").asText(null)).toInstant()));
        coin.setAtl(marketData.get("atl").get("usd").asDouble(0));
        coin.setAtlChangePercentage(marketData.get("atl_change_percentage").get("usd").asDouble(0));
        coin.setAtlDate(Date.from(OffsetDateTime.parse(marketData.get("atl_date").get("usd").asText(null)).toInstant()));
        coin.setRoi(marketData.get("roi").asText(null));
        coin.setLastUpdated((marketData.get("last_updated").asText(null)));

        coinRepo.save(coin);
        return coinDetails;
    }

    @Override
    public Coin findCoinById(String coinId) throws Exception {
        Optional<Coin> coinOptional = coinRepo.findById(coinId);
        if (coinOptional.isEmpty()) {
            throw new Exception("Coin not found!");
        }
        return coinOptional.get();
    }

    @Override
    public String searchCoin(String keyword) {
        return coinGekkoClient.searchCoin(keyword);
    }

    @Override
    public String getTop50CoinByMarketCap() {
        return coinGekkoClient.getTop50CoinByMarketCap();
    }

    @Override
    public String getTrendingCoins() {
        return coinGekkoClient.getTrendingCoins();
    }
}
