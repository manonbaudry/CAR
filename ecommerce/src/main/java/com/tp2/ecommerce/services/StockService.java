package com.tp2.ecommerce.services;

import com.tp2.ecommerce.entities.Purchase;
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

    public void updateStock(Purchase purchase) {
        //TODO Update Stock after purchase
        List<Stock> stocks = stockRepository.findAll();
        for (Stock stock: stocks) {

        }
    }
}
