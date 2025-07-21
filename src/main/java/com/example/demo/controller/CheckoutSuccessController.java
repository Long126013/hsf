package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CheckoutSuccessController {
    @GetMapping("/checkout-success")
    public String checkoutSuccess() {
        return "checkout-success";
    }
} 