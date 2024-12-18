package com.example.tradingapp.service;

import com.example.tradingapp.Repository.WatchlistRepository;
import com.example.tradingapp.model.Coin;
import com.example.tradingapp.model.User;
import com.example.tradingapp.model.Watchlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public class WatchlistServiceImpl implements  WatchlistService{
    @Autowired
    private WatchlistRepository watchlistRepository;
    @Override
    public Watchlist findUsserWatchlist(Long userId) throws Exception {
        Watchlist watchlist = watchlistRepository.findByUserId(userId);
        if(watchlist == null){
            throw new Exception("Watchlist not found");
        }
        return watchlist;
    }

    @Override
    public Watchlist createWatchlist(User user) {
        Watchlist watchlist = new Watchlist();
        watchlist.setUser(user);
        return watchlistRepository.save(watchlist);
    }

    @Override
    public Watchlist findById(Long id) throws Exception {
        Optional<Watchlist> watchlist = watchlistRepository.findById(id);
        if(watchlist.isEmpty())
            throw new Exception("Watchlist not found");
        return watchlist.get();
    }

    @Override
    public Coin addItemToWatchlist(Coin coin, User user) throws Exception {
        Watchlist watchlist = findUsserWatchlist(user.getId());
        if(watchlist.getCoins().contains(coin))
            watchlist.getCoins().remove(coin);
        else
            watchlist.getCoins().add(coin);
         watchlistRepository.save(watchlist);
         return coin;
    }

}
