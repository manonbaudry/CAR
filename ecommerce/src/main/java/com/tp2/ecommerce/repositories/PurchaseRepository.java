package com.tp2.ecommerce.repositories;

import com.tp2.ecommerce.entities.Customer;
import com.tp2.ecommerce.entities.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findByCustomer(Customer customer);
}
