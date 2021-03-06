package com.tp2.ecommerce.repositories;

import com.tp2.ecommerce.entities.Product;
import com.tp2.ecommerce.entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Long> {
    Stock findByProduct(Product product);

    List<Stock> findByRemainingProductsGreaterThan(int quantity);
}
