package com.example.lr4.service;

import com.example.lr4.entity.Product;
import com.example.lr4.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        try {
            Iterable<Product> iterable = productRepository.findAll();
            iterable.forEach(products::add);
        } catch (
                Exception e) { // Обработка исключений
            System.err.println("Error retrieving products: " + e.getMessage());
        }
        return products;
    }

    public Optional<Product> getProductById(int id) {
        return productRepository.findById(id);
    }

    public Product updateProduct(int id, Product productDetails) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        product.setName(productDetails.getName());
        product.setPrice(productDetails.getPrice());
        return productRepository.save(product);
    }

    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }
}
