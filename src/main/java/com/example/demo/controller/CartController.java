package com.example.demo.controller;

import com.example.demo.model.Cart;
import com.example.demo.model.User;
import com.example.demo.service.CartService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final UserService userService;

    @GetMapping
    public String viewCart(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String email = userDetails.getUsername();
        Cart cart = cartService.getCartByUserEmail(email);
        model.addAttribute("cart", cart);
        model.addAttribute("cartItems", cart.getItems());
        return "cart";
    }

    @PostMapping("/add")
    public String addToCart(@AuthenticationPrincipal UserDetails userDetails, @RequestParam UUID productId, @RequestParam(defaultValue = "1") int quantity) {
        String email = userDetails.getUsername();
        cartService.addToCart(productId, email, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/checkout")
    public Object checkout(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        Cart cart = cartService.getCartByUserEmail(email);
        long total = cart.getTotal();
        User user = cart.getUser();
        if (total == 0) {
            return ResponseEntity.badRequest().body("Cart is empty!");
        }
        if (user.getBalance() < total) {
            return ResponseEntity.badRequest().body("Insufficient balance!");
        }
        user.setBalance(user.getBalance() - total);
        cart.getItems().clear();
        cartService.saveCart(cart);
        userService.saveUser(user);
        return new org.springframework.web.servlet.view.RedirectView("/checkout-success");
    }
}
