package com.tp2.ecommerce.controllers;

import com.tp2.ecommerce.entities.Product;
import com.tp2.ecommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.tp2.ecommerce.services.ProductService;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping(path = "/products")
    public @ResponseBody
    List<Product> getAll(){
        return productService.findAll();
    }

}
