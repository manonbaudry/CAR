package com.tp2.ecommerce.services;

import com.tp2.ecommerce.entities.Stock;
import com.tp2.ecommerce.repositories.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    public List<Stock> findAll(){
        return stockRepository.findAll();
    }

}
