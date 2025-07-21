package com.example.demo.controller;

import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class DemoController {
    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(DemoController.class);

    @PostMapping("/demo/update-balance")
    public RedirectView updateBalance(@RequestParam UUID userId, @RequestParam long amount) {
        logger.info("Received balance update request: userId={}, amount={}", userId, amount);
        try {
            userService.increaseBalance(userId, amount);
            logger.info("Balance updated successfully for userId={}", userId);
            return new RedirectView("/payment-success");
        } catch (Exception e) {
            logger.error("Failed to update balance for userId={}: {}", userId, e.getMessage());
            return new RedirectView("/payment-success?error=" + e.getMessage());
        }
    }
} 