package com.tp2.ecommerce.entities;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Product {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private double price;
}
