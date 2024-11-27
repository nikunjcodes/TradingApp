package com.example.tradingapp.controller;

import com.example.tradingapp.model.Coin;
import com.example.tradingapp.service.CoinService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coins")
public class CoinController {
    @Autowired
    private CoinService coinService;
    @Autowired
    private ObjectMapper objectMapper;
@GetMapping
    ResponseEntity<List<Coin>> getCoinList(@RequestParam("page") int page) {
        return ResponseEntity.ok(coinService.getCoinList(page));

    }
    @GetMapping("/{coinId}/chart")
    ResponseEntity<JsonNode> getMarketChart(@PathVariable String coinId , @RequestParam("days") int days) throws JsonProcessingException {
        String res = coinService.getMarketChart(coinId , days);
        JsonNode jsonNode = objectMapper.readTree(res);
        return new ResponseEntity<>(jsonNode , HttpStatus.ACCEPTED);

    }
    @GetMapping("/search")
    ResponseEntity<JsonNode> searchCoin(@RequestParam("q") String keyword) throws JsonProcessingException {
        String res = coinService.searchCoin(keyword);
        JsonNode jsonNode = objectMapper.readTree(res);
        return new ResponseEntity<>(jsonNode , HttpStatus.ACCEPTED);
    }
    @GetMapping("/top50")
    ResponseEntity<JsonNode> getTop50CoinByMarketCapRank() throws JsonProcessingException {
        String res = coinService.getTop50CoinByMarketCapRank();
        JsonNode jsonNode = objectMapper.readTree(res);
        return new ResponseEntity<>(jsonNode , HttpStatus.ACCEPTED);
    }
    @GetMapping("/trending")
    ResponseEntity<JsonNode> getTradingCoins() throws JsonProcessingException{
    String coin  = coinService.getTradingCoins();
    JsonNode jsonNode = objectMapper.readTree(coin);
    return  ResponseEntity.ok(jsonNode);
    }
    @GetMapping("/details/{coinId}")
    ResponseEntity<JsonNode> getCoinDetails(@PathVariable String coinId) throws JsonProcessingException{
    String coin = coinService.getCoinDetails(coinId);
    JsonNode jsonNode= objectMapper.readTree(coin);
    return  ResponseEntity.ok(jsonNode);

    }


}
