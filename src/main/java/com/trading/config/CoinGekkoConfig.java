package com.trading.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 15-08-2025 13:36
 */
@Configuration
public class CoinGekkoConfig {
    @Value("${coin.gekko.base.url}")
    private String coinGekkoBaseUrl;

    @Bean(name = "coinGekkoWebClient")
    public WebClient coinGekkoWebClient() {
        return WebClient.builder().baseUrl(coinGekkoBaseUrl)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
