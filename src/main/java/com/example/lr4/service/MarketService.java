package com.example.lr4.service;

import com.example.lr4.entity.Market;
import com.example.lr4.repository.MarketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MarketService {
    @Autowired
    private MarketRepository marketRepository;

    public Market createMarket(Market market) {
        return marketRepository.save(market);
    }

    public List<Market> getAllMarkets() {
        List<Market> markets = new ArrayList<>();
        try {
            Iterable<Market> iterable = marketRepository.findAll();
            for (Market market : iterable) {
                markets.add(market);
            }
        } catch (Exception e) {
            System.err.println("Ошибка при получении списка рынков: " + e.getMessage());
        }
        return markets;
    }


    public Optional<Market> getMarketById(int id) {
        return marketRepository.findById(id);
    }

    public Market updateMarket(int id, Market newMarket) {
        Market market = marketRepository.findById(id).orElseThrow(() -> new RuntimeException("Market not found"));
        market.setName(newMarket.getName());
        return marketRepository.save(market);
    }

    public void deleteMarket(int id) {
        marketRepository.deleteById(id);
    }
}
