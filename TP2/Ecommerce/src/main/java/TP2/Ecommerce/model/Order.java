package TP2.Ecommerce.model;

import javax.persistence.*;

@Entity
public class Order {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Customer customer;


}
