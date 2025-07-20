package com.example.demo.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDTO {
    private UUID productId;
    private String productName;
    private long price;
    private int quantity;
    private long totalPrice;
}