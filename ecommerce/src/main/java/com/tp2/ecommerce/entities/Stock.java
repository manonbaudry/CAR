package com.tp2.ecommerce.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Stock {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany
    private List<Product> products;

}
