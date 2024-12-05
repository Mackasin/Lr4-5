package com.example.lr4.controller;

import com.example.lr4.entity.Cart;
import com.example.lr4.entity.Market;
import com.example.lr4.service.CartService;
import com.example.lr4.service.MarketService;
import com.example.lr4.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MarketController {

    @Autowired
    private MarketService marketService;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

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
        marketService.deleteMarket(id);
        return "redirect:/markets";
    }

    @GetMapping("/{marketId}/carts")
    public String viewCartsForMarket(@PathVariable int marketId, Model model) {
        List<Cart> carts = cartService.getAllCartsForMarket(marketId);
        model.addAttribute("carts", carts);
        model.addAttribute("marketId", marketId); // Передаем marketId в шаблон
        return "carts";
    }

    @PostMapping("/{marketId}/saveCart")
    public String saveCart(@PathVariable int marketId) {
        cartService.createCart(marketId);
        return "redirect:/{marketId}/carts";
    }

    @GetMapping("/carts/{cartId}/products")
    public String viewProductsInCart(@PathVariable int cartId, Model model) {
        Cart cart = cartService.getCartById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        model.addAttribute("cart", cart);
        model.addAttribute("products", cart.getProducts());
        return "cart_products";
    }

    @PostMapping("/carts/{cartId}/addProduct")
    public String addProductToCart(@PathVariable int cartId, @RequestParam int productId) {
        try {
            cartService.addProductToCart(cartId, productId);
        } catch (Exception e) {
            // Логирование ошибки (опционально)
            System.err.println("Error adding product to cart: " + e.getMessage());
            // Перенаправление в случае ошибки
            return "redirect:/carts/" + cartId + "/products?error=true";
        }
        // Успешное добавление продукта
        return "redirect:/carts/" + cartId + "/products";
    }


    @PostMapping("/carts/{cartId}/removeProduct")
    public String removeProductFromCart(@PathVariable int cartId, @RequestParam int productId) {
        cartService.removeProductFromCart(cartId, productId);
        return "redirect:/carts/{cartId}/products";
    }

    @GetMapping("/deleteCart/{cartId}")
    public String deleteCart(@PathVariable int cartId) {
        cartService.deleteCart(cartId);
        return "redirect:/markets";
    }
}
