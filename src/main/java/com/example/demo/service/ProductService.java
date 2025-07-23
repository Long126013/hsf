package com.example.demo.service;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public void createProduct(Product product){
        productRepository.save(product);
    }

    public Product findById(UUID id){
        return productRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Product not found!"));
    }

    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public List<Product> findByName(String name){
        return productRepository.findAllByNameContainingIgnoreCase(name);
    }

    public void deleteProductById(UUID id) {
        productRepository.deleteById(id);
    }

    public Product getProductById(UUID id){
        return productRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Product not found!"));
    }

    public void updateProduct(Product product) {
        productRepository.save(product);
    }

    public List<Product> filterProducts(String keyword, Long minPrice, Long maxPrice, Integer minQty, Integer maxQty) {
        return productRepository.findAll().stream()
            .filter(p -> keyword == null || keyword.isBlank() || p.getName().toLowerCase().contains(keyword.toLowerCase()))
            .filter(p -> minPrice == null || p.getPrice() >= minPrice)
            .filter(p -> maxPrice == null || p.getPrice() <= maxPrice)
            .filter(p -> minQty == null || p.getQuantity() >= minQty)
            .filter(p -> maxQty == null || p.getQuantity() <= maxQty)
            .toList();
    }

    public List<Product> filterProductsPage(String keyword, Long minPrice, Long maxPrice, Integer minQty, Integer maxQty, int page, int size) {
        List<Product> filtered = filterProducts(keyword, minPrice, maxPrice, minQty, maxQty);
        int from = Math.max(0, (page - 1) * size);
        int to = Math.min(filtered.size(), from + size);
        if (from >= filtered.size()) return List.of();
        return filtered.subList(from, to);
    }
}
