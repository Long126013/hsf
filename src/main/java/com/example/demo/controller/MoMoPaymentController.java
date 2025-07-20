package com.example.demo.controller;

import com.example.demo.service.MoMoPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/momo")
public class MoMoPaymentController {
    @Autowired
    private MoMoPaymentService momoPaymentService;

    @GetMapping("/topup")
    @ResponseBody
    public Map<String, Object> topUp(@RequestParam UUID userId, @RequestParam long amount) {
        String orderId = UUID.randomUUID().toString();
        String orderInfo = "Top up for user: " + userId;
        Map<String, Object> response = momoPaymentService.createPaymentRequest(orderId, amount, orderInfo);
        String payUrl = (String) response.get("payUrl");
        Map<String, Object> result = new HashMap<>();
        result.put("payUrl", payUrl);
        return result;
    }
} 