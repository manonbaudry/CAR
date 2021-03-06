package com.tp2.ecommerce.services;

import com.tp2.ecommerce.entities.Customer;
import com.tp2.ecommerce.exceptions.ConnectionRefused;
import com.tp2.ecommerce.exceptions.IdNotFoundException;
import com.tp2.ecommerce.exceptions.MailAlreadyExistException;
import com.tp2.ecommerce.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> findAll(){
        return customerRepository.findAll();
    }

    public Customer findById(long id) throws IdNotFoundException {
        Customer customer = customerRepository.findById(id);
        if (customer == null) {
            throw new IdNotFoundException("The given customer id was not found");
        }
        return customer;
    }

    public void createCustomer(Customer customer) throws MailAlreadyExistException {
        if (customerRepository.findByMail(customer.getMail()) == null) {
            customerRepository.save(customer);
        }
        throw new MailAlreadyExistException("The given mail is already in use");
    }

    public Customer connect(String mail, String pwd) throws ConnectionRefused {
        Customer customer = customerRepository.findByMailAndPassword(mail, pwd);
        if (customer == null) {
            throw new ConnectionRefused("Incorrect email or password");
        }
        return customer;
    }
}
