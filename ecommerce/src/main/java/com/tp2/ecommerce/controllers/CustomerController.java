package com.tp2.ecommerce.controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tp2.ecommerce.entities.Customer;
import com.tp2.ecommerce.exceptions.ConnectionRefused;
import com.tp2.ecommerce.exceptions.IdNotFoundException;
import com.tp2.ecommerce.exceptions.MailAlreadyExistException;
import com.tp2.ecommerce.services.CustomerService;
import com.tp2.ecommerce.services.ProductService;
import com.tp2.ecommerce.services.StockService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;


/**
 * Customer controller.
 * Use connection service. Authent & Routing
 */
@RequestMapping("/")
@Controller
public class CustomerController {
	@Autowired
	private CustomerService customerService;
	@Autowired
	private StockService stockService;
	
	/**
	 * Connection routing method
	 */
	@GetMapping(value = { "/", "/index","/connection"})
	public String connection(Model model) {
		String message = "Sign in";
		model.addAttribute("message", message);
		return "connection";
	}
	
	/**
	 * Inscription routing method.
	 */
	@GetMapping(value = { "/inscription"})
	public String inscription(Model model) {
		String message = "Sign up";
		model.addAttribute("message", message);
		return "inscription";
	}
	
	/**
	 * Check if the given user is in database
	 * @param customerForm - datas from front 
	 * @param result - result get 
	 * @param model - customer model 
	 * @param session - current user session
	 * @return path productlist if the user is authent, connection if authent failed.
	 */
	@GetMapping(value = "/checkLogin")
	public String checkLogin(@ModelAttribute("userFormData") Customer customerForm, BindingResult 
	result, Model model,  HttpSession session) throws com.tp2.ecommerce.exceptions.ConnectionRefused, NotFoundException {
		if(customerService.connect(customerForm.getMail(), customerForm.getPassword())!=null) {
			Customer connectedCustomer =  customerService.findByMail(customerForm.getMail());
			customerService.addCustomerToSession(session, connectedCustomer);
			String message = "Welcome " + connectedCustomer.getName() + " !";
			model.addAttribute("message", message);
			model.addAttribute("stock", stockService.findAll() );
		    return "productList";
		}else {
			String message = "Connection failed. Try Again";
			model.addAttribute("message", message);
			return "connection";
		}	    
	}

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
