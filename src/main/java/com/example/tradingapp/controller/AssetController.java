package com.example.tradingapp.controller;

import com.example.tradingapp.model.Asset;
import com.example.tradingapp.service.AssetService;
import com.example.tradingapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/asset")

public class AssetController {
    @Autowired
    private AssetService assetService;
    @Autowired
    private UserService userService;
    @GetMapping("/{assetId}")
    public ResponseEntity<Asset> getAssetById(@PathVariable Long assetId) throws Exception{
        return ResponseEntity.ok(assetService.getAssetById(assetId));
    }
    @GetMapping("/coin/{coinId}/user")
    public ResponseEntity<Asset> getAssetByUserAndCoinId(@PathVariable String coinId ,
                                                         @RequestHeader("Authorization") String jwt) throws Exception{
        return ResponseEntity.ok(assetService.findAssesByUserIdAndCoinId(userService.findUserProfileByJwt(jwt).getId() , coinId));

    }
    @GetMapping()
    public ResponseEntity<List<Asset>> getAssetForUser(@RequestHeader("Authorization") String jwt) throws Exception{
        return ResponseEntity.ok(assetService.getUsersAsset(userService.findUserProfileByJwt(jwt).getId()));
    }

}
