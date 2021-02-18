package com.tp2.ecommerce.repositories;

import com.tp2.ecommerce.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository <Customer, Long> {
}
