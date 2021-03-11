package com.tp2.ecommerce.controllers;

import com.tp2.ecommerce.entities.Customer;
import com.tp2.ecommerce.exceptions.ConnectionRefused;
import com.tp2.ecommerce.exceptions.IdNotFoundException;
import com.tp2.ecommerce.exceptions.MailAlreadyExistException;
import com.tp2.ecommerce.services.CustomerService;
import com.tp2.ecommerce.utils.ConnectionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @CrossOrigin
    @GetMapping(path = "/customers")
    public List<Customer> getAll(){
        return customerService.findAll();
    }

    @CrossOrigin
    @GetMapping(path = "/{id}")
    public Customer getById(@PathVariable("id") long id) throws IdNotFoundException {
        return customerService.findById(id);
    }

    @CrossOrigin
    @GetMapping
    public Customer connect(@RequestBody ConnectionInfo connectionInfo) throws ConnectionRefused {
        return customerService.connect(connectionInfo);
    }

    @CrossOrigin
    @PostMapping()
    public Customer createCustomer(@RequestBody Customer customer) throws MailAlreadyExistException {
        return customerService.createCustomer(customer);
    }

    @CrossOrigin
    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public String mailAlreadyExistHandler(MailAlreadyExistException e) {
        return e.getMessage();
    }

    @CrossOrigin
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String idNotFoundHandler(IdNotFoundException e) {
        return e.getMessage();
    }

    @CrossOrigin
    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String ConnectionRefused(ConnectionRefused e) {
        return e.getMessage();
    }

}
