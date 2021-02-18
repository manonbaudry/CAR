package entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@Entity
public class Order {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Customer customer;


}
