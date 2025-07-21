package com.example.demo.service;

import com.example.demo.model.Cart;
import com.example.demo.model.CartItem;
import com.example.demo.model.User;
import com.example.demo.repository.CartItemRepository;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    public Cart getCartByUserEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });
    }

    public void removeItemFromCart(UUID cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    public void updateQuantity(UUID cartItemId, int newQuantity) {
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new IllegalArgumentException("Cart item not found"));
        item.setQuantity(newQuantity);
        cartItemRepository.save(item);
    }

    public void saveCart(Cart cart) {
        cartRepository.save(cart);
    }
}
