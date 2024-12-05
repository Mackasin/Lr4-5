package com.example.lr4.repository;

import com.example.lr4.entity.Cart;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CartRepository extends CrudRepository<Cart, Integer> {
    List<Cart> findByMarketId(int marketId);
}