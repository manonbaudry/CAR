package com.tp2.ecommerce.services;

import com.tp2.ecommerce.entities.Purchase;
import com.tp2.ecommerce.repositories.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    public List<Purchase> findAll(){
        return purchaseRepository.findAll();
    }
}
