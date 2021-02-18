package com.tp2.ecommerce.services;

import com.tp2.ecommerce.entities.ProdOrder;
import com.tp2.ecommerce.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<ProdOrder> findAll(){
        return orderRepository.findAll();
    }
}
