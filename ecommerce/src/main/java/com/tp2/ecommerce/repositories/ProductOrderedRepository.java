package com.tp2.ecommerce.repositories;

import com.tp2.ecommerce.entities.ProductOrdered;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOrderedRepository extends JpaRepository<ProductOrdered, Long> {

}
