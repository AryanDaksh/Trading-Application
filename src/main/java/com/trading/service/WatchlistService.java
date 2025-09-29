package com.trading.service;

import com.trading.entity.Coin;
import com.trading.entity.User;
import com.trading.entity.Watchlist;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 03-09-2025 23:29
 */
public interface WatchlistService {
    Watchlist findUserWatchlist(final Long userId) throws Exception;
    Watchlist createUserWatchlist(final User user);
    Watchlist findByWatchlistId(final Long id) throws Exception;
    Coin addCoinToWatchlist(final Coin coin, final User user) throws Exception;
}
