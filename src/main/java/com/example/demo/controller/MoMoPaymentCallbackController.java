package com.example.demo.controller;

import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/api/momo")
public class MoMoPaymentCallbackController {
    @Autowired
    private UserService userService;

    @PostMapping("/success")
    public RedirectView handleMomoCallback(@RequestBody Map<String, Object> payload) {
        String userIdStr = (String) payload.get("userId");
        String amountStr = String.valueOf(payload.get("amount"));
        String resultCode = String.valueOf(payload.get("resultCode"));
        if ("0".equals(resultCode)) { // 0 means success in MoMo
            try {
                UUID userId = UUID.fromString(userIdStr);
                long amount = Long.parseLong(amountStr);
                userService.increaseBalance(userId, amount);
                return new RedirectView("/payment-success");
            } catch (Exception e) {
                return new RedirectView("/payment-success?error=" + e.getMessage());
            }
        }
        return new RedirectView("/payment-success?error=fail");
    }
} 