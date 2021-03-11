package com.tp2.ecommerce.entities;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

/**
 * Stock model. Include stock of products
 *
 */
@Data
@Entity
@RequiredArgsConstructor
public class Stock {
    @Id
    @GeneratedValue
    private int id;
    private int remainingProducts;
    @OneToOne
    private Product product;

    public Stock(int remainingProducts, Product product) {
        this.remainingProducts = remainingProducts;
        this.product = product;
    }
}
