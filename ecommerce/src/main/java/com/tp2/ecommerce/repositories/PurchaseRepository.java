package com.tp2.ecommerce.repositories;

import com.tp2.ecommerce.entities.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
