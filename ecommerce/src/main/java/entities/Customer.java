package entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
public class Customer {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String lastName;
    private String mail;
    @OneToMany
    private List<Order> orders;
}
