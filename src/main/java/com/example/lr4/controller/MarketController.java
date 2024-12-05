package com.example.lr4.controller;

import com.example.lr4.entity.Market;
import com.example.lr4.service.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MarketController {
    @Autowired
    private MarketService marketService;

    @GetMapping("/markets")
    public String viewMarketsPage(Model model) {
        List<Market> markets = marketService.getAllMarkets();
        model.addAttribute("markets", markets);
        return "markets";
    }

    @GetMapping("/showNewMarketForm")
    public String showNewMarketForm(Model model) {
        Market market = new Market();
        model.addAttribute("market", market);
        return "new_market";
    }

    @PostMapping("/saveMarket")
    public String saveMarket(@ModelAttribute("market") Market market) {
        marketService.createMarket(market);
        return "redirect:/markets";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") int id, Model model) {
        Market market = marketService.getMarketById(id).orElseThrow(() -> new RuntimeException("Market not found"));
        model.addAttribute("market", market);
        return "update_market";
    }

    @PostMapping("/updateMarket/{id}")
    public String updateMarket(@PathVariable(value = "id") int id, @ModelAttribute("market") Market newMarket) {
        marketService.updateMarket(id, newMarket);
        return "redirect:/markets";
    }

    @GetMapping("/deleteMarket/{id}")
    public String deleteMarket(@PathVariable(value = "id") int id) {
        this.marketService.deleteMarket(id);
        return "redirect:/markets";
    }
}