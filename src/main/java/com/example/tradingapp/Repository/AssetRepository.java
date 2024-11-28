package com.example.tradingapp.Repository;

import com.example.tradingapp.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssetRepository extends JpaRepository<Asset, Long> {
    List<Asset> findByUserId(Long userId);

    Asset findByUserIdAndCoinId(long userId, String coinId);
}
