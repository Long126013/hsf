package com.example.demo.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDTO {
    private UUID cartId;
    private List<CartItemDTO> items;
    private long total;
}
