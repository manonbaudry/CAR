package com.tp2.ecommerce.controllers;

import com.tp2.ecommerce.entities.Customer;
import com.tp2.ecommerce.entities.Product;
import com.tp2.ecommerce.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping(path = "/customers")
    public List<Customer> getAll(){
        return customerService.findAll();
    }

}
