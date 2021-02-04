package TP2.Ecommerce.controller;

@Controller
public class HelloWorldController {

    @RequestMapping("/")
    @ResponseBody
    public String simple() {
    return "Hello world!";
}
}