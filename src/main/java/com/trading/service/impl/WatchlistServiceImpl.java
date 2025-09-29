package com.trading.service.impl;

import com.trading.entity.Coin;
import com.trading.entity.User;
import com.trading.entity.Watchlist;
import com.trading.repository.WatchlistRepo;
import com.trading.service.WatchlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 03-09-2025 23:29
 */
@RequiredArgsConstructor
@Service
public class WatchlistServiceImpl implements WatchlistService {
    private final WatchlistRepo watchlistRepo;

    @Override
    public Watchlist findUserWatchlist(final Long userId) throws Exception {
        Watchlist watchlist = watchlistRepo.findByUserId(userId);
        if(watchlist == null) {
            throw new Exception("Watchlist not found");
        }
        return watchlist;
    }

    @Override
    public Watchlist createUserWatchlist(final User user) {
        Watchlist watchlist = new Watchlist();
        watchlist.setUser(user);
        return watchlistRepo.save(watchlist);
    }

    @Override
    public Watchlist findByWatchlistId(final Long id) throws Exception {
        Optional<Watchlist> optionalWatchList = watchlistRepo.findById(id);
        if (optionalWatchList.isEmpty()) {
            throw new Exception("Watchlist not found");
        }
        return optionalWatchList.get();
    }

    @Override
    public Coin addCoinToWatchlist(final Coin coin, final User user) throws Exception {
        Watchlist watchList = findUserWatchlist(user.getId());
        if(watchList.getCoins().contains(coin)) {
            watchList.getCoins().remove(coin);
        } else {
            watchList.getCoins().add(coin);
        }
        watchlistRepo.save(watchList);
        return coin;
    }
}
