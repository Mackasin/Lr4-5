package com.example.lr4.service;

import com.example.lr4.entity.Cart;
import com.example.lr4.entity.Product;
import com.example.lr4.repository.CartRepository;
import com.example.lr4.repository.ProductRepository;
import com.example.lr4.repository.MarketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private MarketRepository marketRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<Cart> getAllCartsForMarket(int marketId) {
        List<Cart> carts = new ArrayList<>();
        try {
            carts = cartRepository.findByMarketId(marketId);
        } catch (Exception e) {
            // Обработка исключений
            System.err.println("Error retrieving carts for market: " + e.getMessage());
        }
        return carts;
    }

    public Cart createCart(int marketId) {
        Cart cart = new Cart();
        cart.setMarket(marketRepository.findById(marketId).orElseThrow(() -> new RuntimeException("Market not found")));
        return cartRepository.save(cart);
    }

    public Optional<Cart> getCartById(int id) {
        return cartRepository.findById(id);
    }

    public Cart updateCart(int id, Cart cartDetails) {
        Cart cart = cartRepository.findById(id).orElseThrow(() -> new RuntimeException("Cart not found"));
        cart.setMarket(cartDetails.getMarket());
        cart.setProducts(cartDetails.getProducts());
        return cartRepository.save(cart);
    }

    public void deleteCart(int id) {
        cartRepository.deleteById(id);
    }

    public void addProductToCart(int cartId, int productId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        cart.getProducts().add(product);
        cartRepository.save(cart);
    }

    public void removeProductFromCart(int cartId, int productId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        cart.getProducts().remove(product);
        cartRepository.save(cart);
    }
}
