package com.tp2.ecommerce.services;

import com.tp2.ecommerce.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import com.tp2.ecommerce.repositories.ProductRepository;

import java.util.List;

public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll(){
        return productRepository.findAll();
    }
}
