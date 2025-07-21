package com.example.demo.controller;

import com.example.demo.dto.CartDTO;
import com.example.demo.model.Cart;
import com.example.demo.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping
    public String viewCart(Model model) {
        Cart cart = cartService.getCartByUserId();

        model.addAttribute("cart", cart);
        model.addAttribute("cartItems", cart.getItems());

        return "cart";
    }
}
