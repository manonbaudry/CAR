package com.tp2.ecommerce.services;

import com.tp2.ecommerce.entities.Product;
import com.tp2.ecommerce.entities.ProductOrdered;
import com.tp2.ecommerce.entities.Purchase;
import com.tp2.ecommerce.entities.Stock;
import com.tp2.ecommerce.exceptions.ConnectionRefused;
import com.tp2.ecommerce.exceptions.EmptyStockException;
import com.tp2.ecommerce.repositories.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Stock service. Used to maintain and update stocks
 */
@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    public List<Stock> findAll(){
        return stockRepository.findAll();
    }

    public List<Stock> updateStock(Purchase purchase) throws EmptyStockException {
        int remainingProducts;
        for(ProductOrdered productOrdered : purchase.getProducts()){
            Product product = productOrdered.getProduct();
            Stock stock = stockRepository.findByProduct(product);
            remainingProducts = stock.getRemainingProducts() - productOrdered.getQuantity();
            if(remainingProducts < 0) {
                throw new EmptyStockException("This product is out of stock");
            }
            stock.setRemainingProducts(remainingProducts);
            stockRepository.save(stock);
        }
        return stockRepository.findAll();
    }

    public List<Stock> getProductInStock() {
        return stockRepository.findByRemainingProductsGreaterThan(0);
    }
}
