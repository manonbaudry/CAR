package com.tp2.ecommerce.repositories;

import com.tp2.ecommerce.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long> {
}
