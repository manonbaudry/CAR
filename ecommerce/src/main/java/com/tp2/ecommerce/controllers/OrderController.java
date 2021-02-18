package com.tp2.ecommerce.controllers;

import com.tp2.ecommerce.entities.ProdOrder;
import com.tp2.ecommerce.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping(path = "/orders")
    public List<ProdOrder> getAll(){
        return orderService.findAll();
    }
}
