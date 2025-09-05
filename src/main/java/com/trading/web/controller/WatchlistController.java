package com.trading.web.controller;

import com.trading.entity.Coin;
import com.trading.entity.User;
import com.trading.entity.Watchlist;
import com.trading.service.CoinService;
import com.trading.service.UserService;
import com.trading.service.WatchlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 03-09-2025 23:38
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/watchlist")
public class WatchlistController {
    private final UserService userService;
    private final WatchlistService watchlistService;
    private final CoinService coinService;

    @GetMapping("/user")
    public ResponseEntity<Watchlist> getUserWatchlist(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Watchlist watchlist = watchlistService.findUserWatchlist(user.getId());
        return new ResponseEntity<>(watchlist, HttpStatus.OK);
    }

    @GetMapping("/{watchlistId}")
    public ResponseEntity<Watchlist> getWatchlistById(@PathVariable Long watchlistId) throws Exception {
        Watchlist watchlist = watchlistService.findByWatchlistId(watchlistId);
        return new ResponseEntity<>(watchlist, HttpStatus.OK);
    }

    @PatchMapping("/add/coin/{coinId}")
    public ResponseEntity<Coin> addItemToWatchlist(@RequestHeader("Authorization") String jwt, @PathVariable String coinId) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Coin coin = coinService.findCoinById(coinId);
        Coin addedCoin = watchlistService.addCoinToWatchlist(coin, user);
        return new ResponseEntity<>(addedCoin, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Watchlist> createUserWatchlist(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Watchlist watchlist = watchlistService.createUserWatchlist(user);
        return new ResponseEntity<>(watchlist, HttpStatus.CREATED);
    }
}
