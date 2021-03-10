package com.tp2.ecommerce;

import com.tp2.ecommerce.entities.*;
import com.tp2.ecommerce.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Configuration
@SpringBootApplication
public class EcommerceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceApplication.class, args);
    }

    /***
     * Initialize database
     * @param productRepository
     * @param customerRepository
     * @return insert data
     */
    @Bean
    CommandLineRunner init(ProductRepository productRepository, CustomerRepository customerRepository,
                           StockRepository stockRepository, PurchaseRepository purchaseRepository,
                           ProductOrderedRepository productOrderedRepository) {
        return args -> {
            List<Product> products = initializeProducts();
            productRepository.saveAll(products);

            List<Customer> customers = initializeCustomers();
            customerRepository.saveAll(customers);

            List<Stock> stocks = initializeStocks(products);
            stockRepository.saveAll(stocks);

            List<ProductOrdered> productsOrdered = initializeProductsOrdered(products);
            productOrderedRepository.saveAll(productsOrdered);

            Purchase purchase1 = new Purchase(LocalDateTime.now(), customers.get(0), productsOrdered);
            Purchase purchase2 = new Purchase(LocalDateTime.now(), customers.get(1), productsOrdered);
            purchaseRepository.save(purchase1);
            purchaseRepository.save(purchase2);

        };
    }

    private List<ProductOrdered> initializeProductsOrdered(List<Product> products) {
        List<ProductOrdered> productsOrdered = new ArrayList<>();
        ProductOrdered productOrdered1 = new ProductOrdered(products.get(0), 1);
        ProductOrdered productOrdered2 = new ProductOrdered(products.get(1), 1);
        productsOrdered.add(productOrdered1);
        productsOrdered.add(productOrdered2);
        return productsOrdered;
    }

    private List<Stock> initializeStocks(List<Product> products) {
        Stock stock1 = new Stock(50, products.get(0));
        Stock stock2 = new Stock(20, products.get(1));
        Stock stock3 = new Stock(2, products.get(2));
        List<Stock> stocks = new ArrayList<>();
        stocks.add(stock1);
        stocks.add(stock2);
        stocks.add(stock3);
        return stocks;
    }

    private List<Customer> initializeCustomers() {
        Customer brice = new Customer("Brice", "Despelchin", "bdespelchinp@gmail.com", "azerty");
        Customer manon = new Customer("Manon", "Baudry", "mbaudry@gmail.com", "azerty");
        List<Customer> customers = new ArrayList<>();
        customers.add(brice);
        customers.add(manon);
        return customers;
    }

    private List<Product> initializeProducts() {
        Product biere1 = new Product("Karmeliet", 5, "La bière Tripel Karmeliet est l'un des grands classiques des bières belges");
        Product biere2 = new Product("Paix Dieu", 6, "La Paix-Dieu est la seule bière d'abbaye brassée en pleine lune par la Brasserie Caulier.");
        Product biere3 = new Product("Duvel", 6, "La bière Duvel est une bière belge fruitée dont la réputation n'est plus à faire");
        List<Product> products = new ArrayList<>();
        products.add(biere1);
        products.add(biere2);
        products.add(biere3);
        return products;
    }

}
