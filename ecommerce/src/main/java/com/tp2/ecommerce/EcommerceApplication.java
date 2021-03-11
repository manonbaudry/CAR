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

import javax.servlet.http.HttpSession;

@SpringBootApplication
public class EcommerceApplication {

	/**
	 * Main App
	 * @param args - none
	 */
    public static void main(String[] args) {
        SpringApplication.run(EcommerceApplication.class, args);
    }

    /***
     * Initialize database by calling submethods to init stocks
     * @param productRepository - repo where to save products
     * @param customerRepository -  repo where to save customers
     * @param stockRepository -  repo where to save stocks
     * @param purchaseRepository - repo where to save purhcases
     * @param productOrderedRepository - repo where to save product ordered
     * @return insert data - data inserted
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
            purchaseRepository.save(purchase1);
        };
    }

    /**
     * Products initializer
     * @param products - List of products
     * @return list of ordered products
     */
    private List<ProductOrdered> initializeProductsOrdered(List<Product> products) {
        List<ProductOrdered> productsOrdered = new ArrayList<>();
        ProductOrdered productOrdered1 = new ProductOrdered(products.get(0), 1);
        ProductOrdered productOrdered2 = new ProductOrdered(products.get(1), 1);
        productsOrdered.add(productOrdered1);
        productsOrdered.add(productOrdered2);
        return productsOrdered;
    }

    /**
     * Stocks initializer
     * @param products - List of products in stock
     * @return list of updated stocks
     */
    private List<Stock> initializeStocks(List<Product> products) {
        Stock stock1 = new Stock(5, products.get(0));
        Stock stock2 = new Stock(2, products.get(1));
        List<Stock> stocks = new ArrayList<>();
        stocks.add(stock1);
        stocks.add(stock2);
        return stocks;
    }

    /**
     * Customer initializer
     * @return list of customers
     */
    private List<Customer> initializeCustomers() {
        Customer brice = new Customer("Brice", "Despelchin", "a", "a");
        Customer manon = new Customer("Manon", "Baudry", "mbaudry@gmail.com", "azerty");
        List<Customer> customers = new ArrayList<>();
        customers.add(brice);
        customers.add(manon);
        return customers;
    }

    /**
     * Products initializer
     * @return list of products
     */
    private List<Product> initializeProducts() {
        Product lampe = new Product("lampe", 5);
        Product chaise = new Product("chaise", 15);
        List<Product> products = new ArrayList<>();
        products.add(lampe);
        products.add(chaise);
        return products;
    }

}
