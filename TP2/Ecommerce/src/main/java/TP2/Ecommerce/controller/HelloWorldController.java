package TP2.Ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
public class HelloWorldController {
    @RequestMapping("/")
    @ResponseBody
    public String simple() {
    return "Hello world!";
    }
}
