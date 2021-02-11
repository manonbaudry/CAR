package entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Order {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Customer customer;


}
