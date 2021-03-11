package com.tp2.ecommerce.entities;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

/**
 * Stock model. A stock contain a list of products
 *
 */
@Data
@Entity
@RequiredArgsConstructor
public class Stock {
    @Id
    @GeneratedValue
    private Long id;
    private int remainingProducts;
    @OneToOne
    private Product product;

    public Stock(int remainingProducts, Product product) {
        this.remainingProducts = remainingProducts;
        this.product = product;
    }
}
