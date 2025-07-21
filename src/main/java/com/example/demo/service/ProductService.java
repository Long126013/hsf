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
}
