package com.example.tradingapp.Repository;

import com.example.tradingapp.model.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinRepository extends JpaRepository<Coin, String> {
}
