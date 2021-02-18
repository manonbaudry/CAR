package com.tp2.ecommerce;

import entities.Product;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import repositories.ProductRepository;

@Configuration
@SpringBootApplication
public class EcommerceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceApplication.class, args);
    }

//    @Bean
//    CommandLineRunner init(ProductRepository productRepository) {
//        return args -> {
//            productRepository.save(new Product("lampe", 5));
//        };
//    }
}
