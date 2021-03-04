package com.tp2.ecommerce.controllers;

import com.tp2.ecommerce.entities.Customer;
import com.tp2.ecommerce.exceptions.IdNotFoundException;
import com.tp2.ecommerce.exceptions.MailAlreadyExistException;
import com.tp2.ecommerce.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping(path = "/customers")
    public List<Customer> getAll(){
        return customerService.findAll();
    }

    @GetMapping(path = "/{id}")
    public Customer getById(@PathVariable("id") long id) throws IdNotFoundException {
        return customerService.findById(id);
    }

    @PostMapping()
    public void createCustomer(@RequestBody Customer customer) throws MailAlreadyExistException {
        customerService.createCustomer(customer);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public String mailAlreadyExistHandler(MailAlreadyExistException e) {
        return e.getMessage();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String idNotFoundHandler(IdNotFoundException e) {
        return e.getMessage();
    }

}
