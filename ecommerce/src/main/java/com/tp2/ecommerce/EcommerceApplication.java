package com.tp2.ecommerce;

import com.tp2.ecommerce.entities.Customer;
import com.tp2.ecommerce.entities.ProdOrder;
import com.tp2.ecommerce.entities.Product;
import com.tp2.ecommerce.entities.Stock;
import com.tp2.ecommerce.repositories.CustomerRepository;
import com.tp2.ecommerce.repositories.OrderRepository;
import com.tp2.ecommerce.repositories.ProductRepository;
import com.tp2.ecommerce.repositories.StockRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
                           StockRepository stockRepository, OrderRepository orderRepository) {
        return args -> {
            List<Product> products = initializeProducts();
            productRepository.saveAll(products);

            List<Customer> customers = initializeCustomers();
            customerRepository.saveAll(customers);

            List<Stock> stocks = initializeStocks(products);
            stockRepository.saveAll(stocks);

            ProdOrder prodOrder1 = new ProdOrder(LocalDateTime.now(), customers.get(0), products);
            orderRepository.save(prodOrder1);
        };
    }

    private List<Stock> initializeStocks(List<Product> products) {
        Stock stock1 = new Stock(5, products.get(0));
        Stock stock2 = new Stock(2, products.get(1));
        List<Stock> stocks = new ArrayList<>();
        stocks.add(stock1);
        stocks.add(stock2);
        return stocks;
    }

    private List<Customer> initializeCustomers() {
        Customer brice = new Customer("Brice", "Despelchin", "bdespelchinp@gmail.com");
        Customer manon = new Customer("Manon", "Baudry", "mbaudry@gmail.com");
        List<Customer> customers = new ArrayList<>();
        customers.add(brice);
        customers.add(manon);
        return customers;
    }

    private List<Product> initializeProducts() {
        Product lampe = new Product("lampe", 5);
        Product chaise = new Product("chaise", 15);
        List<Product> products = new ArrayList<>();
        products.add(lampe);
        products.add(chaise);
        return products;
    }
}
