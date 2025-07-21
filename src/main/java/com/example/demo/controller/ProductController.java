package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public String productsPage(
            @RequestParam(value = "keyword", required = false) String keyword, // <--- ADD THIS PARAMETER
            Model model // <--- ADD THIS PARAMETER
    ) {
        List<Product> products; // <--- ADD THIS VARIABLE DECLARATION

        if (keyword != null && !keyword.trim().isEmpty()) {
            products = productService.findByName(keyword);
        } else {
            products = productService.findAll(); // Get all products
        }

        model.addAttribute("products", products); // <--- ADD THIS LINE
        model.addAttribute("keyword", keyword);   // <--- ADD THIS LINE

        return "products"; // This maps to src/main/resources/templates/products.html
    }

    // --- Delete Product ---
    // Handles POST request from the delete form
    @PostMapping("/products/delete")
    public String deleteProduct(@RequestParam("id") UUID id) { // Take product ID from request parameter
        productService.deleteProductById(id);
        return "redirect:/products"; // Redirect back to the product list
    }

    // --- Show Add Product Form ---
    @GetMapping("/products/add")
    public String addProduct(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("pageType", "add");
        return "product-form"; // Returns the unified form template
    }

    // --- Show Edit Product Form ---
    @GetMapping("/products/edit")
    public String editProduct(@RequestParam("id") UUID id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("pageType", "edit");
        return "product-form"; // Returns the unified form template
    }

    // --- MERGED: Process Add or Update Product Form Submission ---
    // This method handles POST requests from both /products/add and /products/update
    @PostMapping("/products/save") // <--- MERGED PATHS
    public String saveProduct(@ModelAttribute Product product) {
        // productService.createProduct() internally calls repository.save()
        // which handles both INSERT (if product.id is null/new) and UPDATE (if product.id exists)
        productService.createProduct(product); // Renamed to createProduct, but it's a save-or-update operation
        return "redirect:/products"; // Redirect back to the product list
    }
}
