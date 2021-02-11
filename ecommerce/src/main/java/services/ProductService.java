package services;

import entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import repositories.ProductRepository;

import java.util.List;

public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll(){
        return productRepository.findAll();
    }
}
