package com.example.tradingapp.Repository;

import com.example.tradingapp.model.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WatchlistRepository extends JpaRepository<Watchlist , Long> {
    Watchlist findByUserId(Long userId);
}
