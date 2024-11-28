package com.example.tradingapp.service;

import com.example.tradingapp.model.Coin;
import com.example.tradingapp.model.User;
import com.example.tradingapp.model.Watchlist;

public interface WatchlistService {
    Watchlist findUsserWatchlist(Long userId) throws Exception;
    Watchlist createWatchlist(User user );

    Watchlist findById(Long id) throws Exception;

    Coin addItemToWatchlist(Coin coin , User user) throws Exception;
}
