package com.tp2.ecommerce.controllers;

import com.tp2.ecommerce.entities.Customer;
import com.tp2.ecommerce.entities.Stock;
import com.tp2.ecommerce.repositories.StockRepository;
import com.tp2.ecommerce.services.StockService;

import javassist.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.servlet.http.HttpSession;

@CrossOrigin
@Controller
@RequestMapping("/stock")
public class StockController {

	@Autowired
	private StockService stockService;

	@GetMapping(path = "/stocks")
	public List<Stock> getAll(){
		return stockService.findAll();
	}

	@GetMapping
	public List<Stock> getProductInStock(){
		return stockService.getProductInStock();
	}
}


