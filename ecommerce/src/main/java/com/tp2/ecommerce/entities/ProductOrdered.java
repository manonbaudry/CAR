package com.tp2.ecommerce.entities;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@RequiredArgsConstructor
public class ProductOrdered {
    @Id
    @GeneratedValue
    private Long id;
    private int quantity;
    @ManyToOne
    private Product product;

    public ProductOrdered(Product product, int quantity) {
        this.quantity = quantity;
        this.product = product;
    }
}
