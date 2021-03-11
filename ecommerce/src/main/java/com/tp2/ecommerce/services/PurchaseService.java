package com.tp2.ecommerce.services;

import com.tp2.ecommerce.entities.Customer;
import com.tp2.ecommerce.entities.Purchase;
import com.tp2.ecommerce.entities.Stock;
import com.tp2.ecommerce.exceptions.IdNotFoundException;
import com.tp2.ecommerce.repositories.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Stock> createPurchase(Purchase purchase){
        purchaseRepository.save(purchase);
        return stockService.updateStock(purchase);
    }

    /**
     * Get a list of purchase by customers
     * @param customerId - customer to find
     * @return the order
     */
    public List<Purchase> getCustomerRecord(long customerId) throws IdNotFoundException {
        Customer customer = customerService.findById(customerId);
        List<Purchase> p = purchaseRepository.findByCustomer(customer);
        return p;
    }
}
