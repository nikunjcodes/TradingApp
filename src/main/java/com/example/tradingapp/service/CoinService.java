package com.example.tradingapp.service;

import com.example.tradingapp.model.Coin;

import java.util.List;

public interface CoinService {
    List<Coin> getCoinList(int page);
    String getMarketChart(String coinId , int days);

    String getCoinDetails(String coinId );
    Coin findById(String coinId);

    String searchCoin(String keyWord);

    String getTop50CoinByMarketCapRank();

    String getTradingCoins();
}
