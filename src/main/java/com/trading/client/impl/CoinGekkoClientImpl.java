package com.trading.client.impl;

import com.trading.client.CoinGekkoClient;
import com.trading.entity.Coin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Collections;
import java.util.List;

import static com.trading.utils.AppConstants.*;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 15-08-2025 13:51
 */
@Slf4j
@Service
public class CoinGekkoClientImpl implements CoinGekkoClient {
    private final WebClient webClient;
    private final String baseUrl;

    public CoinGekkoClientImpl(@Qualifier("coinGekkoWebClient") WebClient webClient,
                         @Value("${coin.gekko.base.url}") String baseUrl) {
        this.webClient = webClient;
        this.baseUrl = baseUrl;
    }

    @Override
    public List<Coin> getCoinList() {
        String uri = baseUrl + COINS_LIST_PATH;
        try {
            log.debug("Fetching Coin List from CoinGecko API...");
            return webClient.get()
                    .uri(uri)
                    .retrieve()
                    .bodyToFlux(Coin.class)
                    .collectList()
                    .block();
        } catch (WebClientResponseException ex) {
            log.error("Error response from CoinGecko API. Status: {}, Body: {}", ex.getStatusCode(), ex.getResponseBodyAsString(), ex);
            return Collections.emptyList();
        } catch (Exception ex) {
            log.error("An unexpected error occurred while calling CoinGecko API", ex);
            return Collections.emptyList();
        }
    }

    @Override
    public String getMarketChart(String coinId, int days) {
        String uri = baseUrl + COINS + coinId + MARKET_CHART_PATH + days;
        try {
            log.debug("Fetching Market Chart from CoinGecko API...");
            return webClient.get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (WebClientResponseException ex) {
            log.error("Error response from CoinGecko API. Status: {}, Body: {}", ex.getStatusCode(), ex.getResponseBodyAsString(), ex);
            return null;
        } catch (Exception ex) {
            log.error("An unexpected error occurred while calling CoinGecko API", ex);
            return null;
        }
    }

    @Override
    public String getCoinDetails(String coinId) {
        String uri = baseUrl + COINS + coinId;
        try {
            log.debug("Fetching Coin Details from CoinGecko API...");
            return webClient.get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (WebClientResponseException ex) {
            log.error("Error response from CoinGecko API. Status: {}, Body: {}", ex.getStatusCode(), ex.getResponseBodyAsString(), ex);
            return null;
        } catch (Exception ex) {
            log.error("An unexpected error occurred while calling CoinGecko API", ex);
            return null;
        }
    }

    @Override
    public String searchCoin(String keyword) {
        String uri = baseUrl + SEARCH_COIN_PATH + keyword;
        try {
            log.debug("Searching Coin from CoinGecko API...");
            return webClient.get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (WebClientResponseException ex) {
            log.error("Error response from CoinGecko API. Status: {}, Body: {}", ex.getStatusCode(), ex.getResponseBodyAsString(), ex);
            return null;
        } catch (Exception ex) {
            log.error("An unexpected error occurred while calling CoinGecko API", ex);
            return null;
        }
    }

    @Override
    public String getTop50CoinByMarketCap() {
        String uri = baseUrl + TOP_50_MARKET_CAP_PATH;
        try {
            log.debug("Fetching Top 50 Coins by Market Cap from CoinGecko API...");
            return webClient.get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (WebClientResponseException ex) {
            log.error("Error response from CoinGecko API. Status: {}, Body: {}", ex.getStatusCode(), ex.getResponseBodyAsString(), ex);
            return null;
        } catch (Exception ex) {
            log.error("An unexpected error occurred while calling CoinGecko API", ex);
            return null;
        }
    }

    @Override
    public String getTrendingCoins() {
        String uri = baseUrl + TRENDING_COIN_PATH;
        try {
            log.debug("Fetching Top 50 Coins by Market Cap from CoinGecko API...");
            return webClient.get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (WebClientResponseException ex) {
            log.error("Error response from CoinGecko API. Status: {}, Body: {}", ex.getStatusCode(), ex.getResponseBodyAsString(), ex);
            return null;
        } catch (Exception ex) {
            log.error("An unexpected error occurred while calling CoinGecko API", ex);
            return null;
        }
    }
}
