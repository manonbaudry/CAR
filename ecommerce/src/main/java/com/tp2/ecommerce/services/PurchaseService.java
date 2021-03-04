package com.tp2.ecommerce.services;

import com.tp2.ecommerce.entities.Customer;
import com.tp2.ecommerce.entities.Purchase;
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

    public void createPurchase(Purchase purchase){
        purchaseRepository.save(purchase);
        stockService.updateStock(purchase);
    }

    public List<Purchase> getCustomerRecord(long customerId) {
        Customer customer = customerService.findById(customerId);
        return purchaseRepository.findByCustomer(customer);
    }
}
