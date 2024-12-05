package com.example.lr4.controller;

import com.example.lr4.entity.Product;
import com.example.lr4.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public String viewProductsPage(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/showNewProductForm")
    public String showNewProductForm(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "new_product";
    }

    @PostMapping("/saveProduct")
    public String saveProduct(@ModelAttribute("product") Product product) {
        productService.createProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/showFormForUpdateProduct/{id}")
    public String showFormForUpdateProduct(@PathVariable(value = "id") int id, Model model) {
        Product product = productService.getProductById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        model.addAttribute("product", product);
        return "update_product";
    }

    @PostMapping("/updateProduct/{id}")
    public String updateProduct(@PathVariable(value = "id") int id, @ModelAttribute("product") Product newProduct) {
        productService.updateProduct(id, newProduct);
        return "redirect:/products";
    }

    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable(value = "id") int id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }
}
