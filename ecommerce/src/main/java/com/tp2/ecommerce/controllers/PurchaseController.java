package com.tp2.ecommerce.controllers;

import com.tp2.ecommerce.entities.Purchase;
import com.tp2.ecommerce.services.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @GetMapping(path = "/purchases")
    public List<Purchase> getAll(){
        return purchaseService.findAll();
    }
}