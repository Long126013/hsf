package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product extends BaseEntity{
    @Column(name = "name", unique = true, nullable = false, length = 255)
    private String name;

    @Column(name = "quantity", nullable = false)
    @Min(value = 0, message = "Quantity must bigger than 0")
    private int quantity;

    @Column(name="price", nullable = false)
    @Min(value = 0, message = "price must bigger than 0")
    private long price;


}
