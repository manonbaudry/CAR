package com.tp2.ecommerce.entities;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

/**
 * Product ordered model. Contains a product and a quantity
 *
 */
@Data
@Entity
@RequiredArgsConstructor
public class ProductOrdered {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private int quantity;
    @ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Product product;

    public ProductOrdered(Product product, int quantity) {
        this.quantity = quantity;
        this.product = product;
    }
}
