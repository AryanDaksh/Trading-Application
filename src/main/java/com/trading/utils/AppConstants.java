package com.trading.utils;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 08-08-2025 20:40
 */
public class AppConstants {
    public static final String JWT_SECRET = "ecwbiujbnvjdnvjnsdaaaaaadfegwegvhgfdSBDTHJRTYM";
    public static final String JWT_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String EMAIL = "email";
    public static final String AUTHORITIES = "authorities";
    public static final int MAX_OTP_ATTEMPTS = 5;
    public static final String COINS_LIST_PATH = "/coins/markets?vs_currency=usd&per_page=10&page=";
    public static final String COINS = "/coins/";
    public static final String MARKET_CHART_PATH = "/market_chart?vs_currency=usd&days=";
}
