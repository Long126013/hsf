package com.example.demo.controller;

import com.example.demo.model.Cart;
import com.example.demo.model.Product;
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

import java.util.List;
import java.util.UUID;
import com.example.demo.model.Product;
import com.example.demo.model.Receipt;
import com.example.demo.model.ReceiptItem;
import com.example.demo.service.ReceiptService;
import java.util.List;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final UserService userService;
    private final ReceiptService receiptService;

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
        // Trừ số lượng sản phẩm
        for (var item : cart.getItems()) {
            Product product = item.getProduct();
            if (product.getQuantity() < item.getQuantity()) {
                return ResponseEntity.badRequest().body("Sản phẩm '" + product.getName() + "' không đủ số lượng tồn kho!");
            }
            product.setQuantity(product.getQuantity() - item.getQuantity());
        }
        user.setBalance(user.getBalance() - total);
        // Tạo hóa đơn (Receipt)
        Receipt receipt = new Receipt();
        receipt.setUser(user);
        receipt.setTotal(total);
        List<ReceiptItem> receiptItems = new java.util.ArrayList<>();
        for (var item : cart.getItems()) {
            ReceiptItem ri = new ReceiptItem();
            ri.setReceipt(receipt);
            ri.setProductName(item.getProduct().getName());
            ri.setPrice(item.getProduct().getPrice());
            ri.setQuantity(item.getQuantity());
            ri.setTotalPrice(item.getTotalPrice());
            receiptItems.add(ri);
        }
        receipt.setItems(receiptItems);
        receiptService.saveReceipt(receipt);
        System.out.println("[DEBUG] Đã tạo hóa đơn với id=" + receipt.getId());
        cart.getItems().clear();
        cartService.saveCart(cart);
        userService.saveUser(user);
        // Chuyển hướng sang trang hóa đơn
        return new org.springframework.web.servlet.view.RedirectView("/receipt/" + receipt.getId());
    }

    @PostMapping("/update")
    public String saveCart(@ModelAttribute("cart") Cart cart) {
        cartService.saveCart(cart);
        return "redirect:/cart";
    }

    @PutMapping("/update/{itemId}")
    public ResponseEntity<?> updateQuantity(@PathVariable UUID itemId, @RequestParam int newQuantity) {
        cartService.updateQuantity(itemId, newQuantity);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/remove/{itemId}")
    @ResponseBody
    public ResponseEntity<?> removeItemFromCart(@PathVariable UUID itemId) {
        cartService.removeItemFromCart(itemId);
        return ResponseEntity.ok().build();
    }
}
