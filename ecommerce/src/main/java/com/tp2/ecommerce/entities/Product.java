package com.tp2.ecommerce.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Product model. A product is an item sell in shop. 
 *
 */
@Data
@Entity
public class Product {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private double price;
    private String description;

    public Product() {}
    public Product(String name, double price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

}
