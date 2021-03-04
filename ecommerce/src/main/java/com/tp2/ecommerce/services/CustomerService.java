package com.tp2.ecommerce.services;

import com.tp2.ecommerce.entities.Customer;
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

    public Customer findById(long id){
        return customerRepository.findById(id);
    }

    public void createCustomer(Customer customer) throws MailAlreadyExistException {
        if (customerRepository.findByMail(customer.getMail()) == null) {
            customerRepository.save(customer);
        }
        throw new MailAlreadyExistException("The given mail is already in use");
    }
}
