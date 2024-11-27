package com.example.tradingapp.service;

import com.example.tradingapp.Repository.CoinRepository;
import com.example.tradingapp.model.Coin;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class CoinServiceImpl implements CoinService {

    @Autowired
    private CoinRepository coinRepository;

    private final ObjectMapper objectMapper = new ObjectMapper(); // Initialized here for simplicity.

    /**
     * Helper method to fetch data from an API.
     */
    private String fetchFromApi(String url) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("API call failed: " + url, e);
        }
    }

    @Override
    public List<Coin> getCoinList(int page) {
        String url = "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&per_page=10&page=" + page;
        try {
            String response = fetchFromApi(url);
            return objectMapper.readValue(response, new TypeReference<List<Coin>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing JSON response for coin list", e);
        }
    }

    @Override
    public String getMarketChart(String coinId, int days) {
        String url = "https://api.coingecko.com/api/v3/coins/" + coinId + "/market_chart?vs_currency=usd&days=" + days;
        return fetchFromApi(url);
    }

    @Override
    public String getCoinDetails(String coinId) {
        String url = "https://api.coingecko.com/api/v3/coins/" + coinId;
        try {
            String response = fetchFromApi(url);
            JsonNode jsonNode = objectMapper.readTree(response);

            Coin coin = new Coin();
            coin.setId(jsonNode.get("id").asText());
            coin.setName(jsonNode.get("name").asText());
            coin.setSymbol(jsonNode.get("symbol").asText());
            coin.setImage(jsonNode.get("image").get("large").asText());

            JsonNode marketData = jsonNode.get("market_data");
            coin.setCurrentPrice(marketData.get("current_price").get("usd").asDouble());
            coin.setMarketCap(marketData.get("market_cap").get("usd").asLong());
            coin.setMarketCapRank(marketData.get("market_cap_rank").asInt());
            coin.setTotalVolume(marketData.get("total_volume").get("usd").asLong());
            coin.setHigh24h(marketData.get("high_24h").get("usd").asDouble());
            coin.setLow24h(marketData.get("low_24h").get("usd").asDouble());
            coin.setPriceChange24h(marketData.get("price_change_24h").asDouble());
            coin.setPriceChangePercentage24h(marketData.get("price_change_percentage_24h").asDouble());
            coin.setMarketCapChange24h(marketData.get("market_cap_change_24h").asDouble());
            coin.setMarketCapChangePercentage24h(marketData.get("market_cap_change_percentage_24h").asDouble());
            coin.setTotalSupply(marketData.get("total_supply").asLong());

            coinRepository.save(coin);
            return response;

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing JSON response for coin details", e);
        }
    }

    @Override
    public Coin findById(String coinId) {
        return coinRepository.findById(coinId)
                .orElseThrow(() -> new RuntimeException("Coin not found with ID: " + coinId));
    }

    @Override
    public String searchCoin(String keyword) {
        String url = "https://api.coingecko.com/api/v3/search?query=" + keyword;
        return fetchFromApi(url);
    }

    @Override
    public String getTop50CoinByMarketCapRank() {
        String url = "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=50&page=1";
        return fetchFromApi(url);
    }

    @Override
    public String getTradingCoins() {
        String url = "https://api.coingecko.com/api/v3/search/trending";
        return fetchFromApi(url);
    }
}
