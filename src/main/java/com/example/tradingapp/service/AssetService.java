package com.example.tradingapp.service;

import com.example.tradingapp.model.Asset;
import com.example.tradingapp.model.Coin;
import com.example.tradingapp.model.User;

import java.util.List;

public interface AssetService {
    Asset createAsset(User user , Coin coin , double quantity);
    Asset getAssetById(Long assetId);

    Asset getAssetByUserIdAndId(Long userId , Long assetId);

    List<Asset> getUsersAsset(Long userId);

    Asset updateAsset(Long assetId , double quantity);

    Asset findAssesByUserIdAndCoinId(Long userId , String coinId);

    void deleteAsset(Long assetId);
}
