package com.example.tradingapp.controller;

import com.example.tradingapp.model.Coin;
import com.example.tradingapp.model.User;
import com.example.tradingapp.model.Watchlist;
import com.example.tradingapp.service.CoinService;
import com.example.tradingapp.service.UserService;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.config.RepositoryNameSpaceHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.tradingapp.service.WatchlistService;
@RestController

public class WatchlistController {
    @Autowired
    private WatchlistService watchlistService;
    @Autowired
    private UserService userService;
    @Autowired
    private CoinService coinService;
    @GetMapping("/user")
    public ResponseEntity<Watchlist> getUserWatchlist(
            @RequestHeader("Authorization") String jwt
    ) throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
        Watchlist createdWatchlist = watchlistService.createWatchlist(user);
        return ResponseEntity.ok(createdWatchlist);
    }
    @PostMapping("/create")
    public ResponseEntity<Watchlist> createWatchlist(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Watchlist createdWatchlist = watchlistService.createWatchlist(user);
        return ResponseEntity.ok(createdWatchlist);
    }
    @GetMapping("/{watchlistId}")
    public ResponseEntity<Watchlist> getWatchlistId(
            @PathVariable Long watchlistId
    ) throws Exception{
        Watchlist watchlist = watchlistService.findById(watchlistId);
        return ResponseEntity.ok(watchlist);
    }
    @PatchMapping("/add/coin/{coinId}")
    public ResponseEntity<Coin> addItemToWatchlist(
            @RequestHeader("Authorization") String jwt,
            @PathVariable String coinId
    ) throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
        Coin coin = coinService.findById(coinId);
        Coin addedCoin = watchlistService.addItemToWatchlist(coin, user);
        return ResponseEntity.ok(addedCoin);
    }
}
