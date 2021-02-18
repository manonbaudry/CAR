package com.tp2.ecommerce.controllers;

import com.tp2.ecommerce.entities.Product;
import com.tp2.ecommerce.entities.Stock;
import com.tp2.ecommerce.repositories.StockRepository;
import com.tp2.ecommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/stock")
public class StockController {

    @Autowired
    private StockRepository stockRepository;

    @GetMapping(path = "/stocks")
    public List<Stock> getAll(){
        return stockRepository.findAll();
    }
}
