package com.tp2.ecommerce.repositories;

import com.tp2.ecommerce.entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {

}
