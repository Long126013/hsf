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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @GetMapping("/products")
    public String productsPage(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "minPrice", required = false) Long minPrice,
            @RequestParam(value = "maxPrice", required = false) Long maxPrice,
            @RequestParam(value = "minQty", required = false) Integer minQty,
            @RequestParam(value = "maxQty", required = false) Integer maxQty,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            Model model
    ) {
        User user = userService.findByEmail(userDetails.getUsername());
        model.addAttribute("user", user);
        int size = 10;
        List<Product> allFiltered = productService.filterProducts(keyword, minPrice, maxPrice, minQty, maxQty);
        int totalPages = (int) Math.ceil((double) allFiltered.size() / size);
        List<Product> products = productService.filterProductsPage(keyword, minPrice, maxPrice, minQty, maxQty, page, size);
        model.addAttribute("products", products);
        model.addAttribute("keyword", keyword);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("minQty", minQty);
        model.addAttribute("maxQty", maxQty);
        model.addAttribute("page", page);
        model.addAttribute("totalPages", totalPages);
        return "products";
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
