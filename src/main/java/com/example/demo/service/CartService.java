package com.example.demo.service;

import com.example.demo.model.Cart;
import com.example.demo.model.CartItem;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.repository.CartItemRepository;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public Cart getCartByUserEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    newCart.setItems(new ArrayList<>());
                    return cartRepository.save(newCart);
                });
    }

    public void addToCart(UUID productId, String email, int quantity) {
        Cart cart = getCartByUserEmail(email);
        Product product = productRepository.findById(productId).orElseThrow();

        // Tính tổng số lượng sản phẩm này trong cart hiện tại
        Optional<CartItem> optionalCartItem = cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();
        int currentQty = optionalCartItem.map(CartItem::getQuantity).orElse(0);
        int availableQty = product.getQuantity();
        if (currentQty + quantity > availableQty) {
            throw new IllegalArgumentException("Số lượng vượt quá tồn kho hiện tại của sản phẩm!");
        }

        if (optionalCartItem.isPresent()) {
            CartItem existingItem = optionalCartItem.get();
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            CartItem newItem = new CartItem();
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            newItem.setCart(cart);
            cart.getItems().add(newItem);
        }

        cartRepository.save(cart);
    }

    public void removeItemFromCart(UUID cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    public void updateQuantity(UUID cartItemId, int newQuantity) {
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new IllegalArgumentException("Cart item not found"));
        Product product = item.getProduct();
        if (newQuantity > product.getQuantity()) {
            throw new IllegalArgumentException("Số lượng cập nhật vượt quá tồn kho hiện tại của sản phẩm!");
        }
        item.setQuantity(newQuantity);
        cartItemRepository.save(item);
    }

    public void saveCart(Cart cart) {
        cartRepository.save(cart);
    }
}
