package com.tp2.ecommerce.repositories;

import com.tp2.ecommerce.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
