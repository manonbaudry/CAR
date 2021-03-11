package com.tp2.ecommerce.controllers;

import com.tp2.ecommerce.entities.Customer;
import com.tp2.ecommerce.entities.Purchase;
import com.tp2.ecommerce.entities.Stock;
import com.tp2.ecommerce.exceptions.IdNotFoundException;
import com.tp2.ecommerce.services.PurchaseService;

import javassist.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Purchase controller.
 * List purchases, purchase by id, cart, etc
 */
@CrossOrigin
@Controller
@RequestMapping("/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @GetMapping(path = "/purchases")
    public List<Purchase> getAll(){
        return purchaseService.findAll();
    }

    @GetMapping(path = "/{customerId}")
    public List<Purchase> getCustomerRecord (@PathVariable("customerId") long customerId) throws IdNotFoundException {
       //TODO Fix Bug
        return purchaseService.getCustomerRecord(customerId);
    }

    @PostMapping
    public List<Stock> createPurchase(@RequestBody Purchase purchase){
        return purchaseService.createPurchase(purchase);
    }
    
    /**
     * Add a product to shopping cart
     */
	@GetMapping("/addToCart")
    public String addToCart(HttpRequest request , Model model) {
       // request.getParameterMap();
        System.out.println(request);        
        return"order";
    }
	
}
