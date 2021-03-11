package com.tp2.ecommerce.services;

import com.tp2.ecommerce.entities.Customer;
import com.tp2.ecommerce.entities.Purchase;
import com.tp2.ecommerce.entities.Stock;
import com.tp2.ecommerce.exceptions.EmptyStockException;
import com.tp2.ecommerce.exceptions.IdNotFoundException;
import com.tp2.ecommerce.repositories.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Purchase service. Used for update stocks and map purchase to customer.
 */
@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private StockService stockService;

    public List<Purchase> findAll(){
        return purchaseRepository.findAll();
    }

    public List<Stock> createPurchase(Purchase purchase) throws EmptyStockException {
        purchaseRepository.save(purchase);
        return stockService.updateStock(purchase);
    }

    public List<Purchase> getCustomerRecord(long customerId) throws IdNotFoundException {
        Customer customer = customerService.findById(customerId);
        List<Purchase> p = purchaseRepository.findByCustomer(customer);
        return p;
    }
}
