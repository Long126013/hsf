package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "receipt_items")
public class ReceiptItem extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receipt_id", nullable = false)
    private Receipt receipt;

    @Column(nullable = false, columnDefinition = "NVARCHAR(255)")
    private String productName;

    @Column(nullable = false)
    private long price;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private long totalPrice;
} 