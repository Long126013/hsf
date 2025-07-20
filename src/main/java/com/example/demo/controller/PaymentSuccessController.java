package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaymentSuccessController {
    @GetMapping("/payment-success")
    public String showPaymentSuccess() {
        return "payment-success";
    }
} 