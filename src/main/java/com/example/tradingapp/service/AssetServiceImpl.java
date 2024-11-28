package com.example.tradingapp.service;

import com.example.tradingapp.Repository.AssetRepository;
import com.example.tradingapp.model.Asset;
import com.example.tradingapp.model.Coin;
import com.example.tradingapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service

public class AssetServiceImpl implements AssetService{
    @Autowired
    private AssetRepository assetRepository;

    @Override
    public Asset createAsset(User user, Coin coin, double quantity) {
        Asset asset = new Asset();
        asset.setUser(user);
        asset.setCoin(coin);
        asset.setQuantity(quantity);
        asset.setBuyPrice(coin.getCurrentPrice());
        return assetRepository.save(asset);
    }

    @Override
    public Asset getAssetById(Long assetId) {
        return assetRepository.findById(assetId).orElseThrow(() -> new RuntimeException("Asset not found"));
    }

    @Override
    public Asset getAssetByUserIdAndId(Long userId, Long assetId) {
        return null;
    }

    @Override
    public List<Asset> getUsersAsset(Long userId) {
        return assetRepository.findByUserId(userId);
    }

    @Override
    public Asset updateAsset(Long assetId, double quantity) {
        Asset oldAsset = getAssetById(assetId);
        oldAsset.setQuantity(oldAsset.getQuantity()+quantity);
        return assetRepository.save(oldAsset);

    }

    @Override
    public Asset findAssesByUserIdAndCoinId(Long userId, String coinId) {
        return assetRepository.findByUserIdAndCoinId(userId, coinId);
    }

    @Override
    public void deleteAsset(Long assetId) {
        assetRepository.deleteById(assetId);
    }
}
