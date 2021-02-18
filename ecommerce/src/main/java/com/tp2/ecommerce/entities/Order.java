package com.tp2.ecommerce.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Order {
    @Id
    @GeneratedValue
    private Long id;

    private Date date;

    @ManyToOne
    private Customer customer;

    @OneToMany
    private List<Product> products;
}
