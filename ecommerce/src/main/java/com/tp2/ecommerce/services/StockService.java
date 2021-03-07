package com.tp2.ecommerce.services;

import com.tp2.ecommerce.entities.Product;
import com.tp2.ecommerce.entities.ProductOrdered;
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

    public List<Stock> updateStock(Purchase purchase) {
        for(ProductOrdered productOrdered : purchase.getProducts()) {
            Product product = productOrdered.getProduct();
            Stock stock = stockRepository.findByProduct(product);
            //TODO Gérer exception si stock < 0
            stock.setRemainingProducts(stock.getRemainingProducts()- productOrdered.getQuantity());
            stockRepository.save(stock);
        }
        return stockRepository.findAll();
    }

    public List<Stock> getProductInStock() {
        return null;
    }
}
