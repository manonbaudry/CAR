package com.tp2.ecommerce.entities;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Purchase model. Contains a list of products
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
    @ManyToOne(cascade = CascadeType.REMOVE)
    private Customer customer;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "product")
    private List<ProductOrdered> products;

    public Purchase(LocalDateTime date, Customer customer, List<ProductOrdered> products) {
        this.date = date;
        this.customer = customer;
        this.products = products;
    }
}
