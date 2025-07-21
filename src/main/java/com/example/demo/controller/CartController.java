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

    @PostMapping("/checkout")
    public String checkout(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        Cart cart = cartService.getCartByUserEmail(email);
        long total = cart.getTotal();
        User user = cart.getUser();
        if (user.getBalance() < total) {
            return "Insufficient balance!";
        }
        user.setBalance(user.getBalance() - total);
        cart.getItems().clear();
        cartService.saveCart(cart);
        userService.saveUser(user);
        return "Checkout successful!";
    }
}
