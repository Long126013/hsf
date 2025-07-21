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
}
