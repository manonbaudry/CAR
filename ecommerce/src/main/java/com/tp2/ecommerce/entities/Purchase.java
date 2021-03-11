package com.tp2.ecommerce.entities;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Purchase model. Map a customer to a list of products  
 * 	
 */
@Data
@Entity
@RequiredArgsConstructor
public class Purchase {
    @Id
    @GeneratedValue
    private Long id;
    private LocalDateTime date;
    @ManyToOne
    private Customer customer;
    @OneToMany
    private List<ProductOrdered> products;

    public Purchase(LocalDateTime date, Customer customer, List<ProductOrdered> products) {
        this.date = date;
        this.customer = customer;
        this.products = products;
    }
}