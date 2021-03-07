package com.tp2.ecommerce.controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tp2.ecommerce.entities.Customer;
import com.tp2.ecommerce.exceptions.ConnectionRefused;
import com.tp2.ecommerce.exceptions.IdNotFoundException;
import com.tp2.ecommerce.exceptions.MailAlreadyExistException;
import com.tp2.ecommerce.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {
	
	@RequestMapping(value = {
	        "",
	        "/connection",
	        "*/*"
	    })
    public String simple() {
	     return "connection";
    }
    
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

    @GetMapping(path = "/connect")
    public Customer connect(@RequestBody ObjectNode connexionInfo) throws ConnectionRefused {
        String mail = connexionInfo.get("mail").asText();
        String password = connexionInfo.get("password").asText();
        return customerService.connect(mail, password);
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

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String ConnectionRefused(ConnectionRefused e) {
        return e.getMessage();
    }

}
