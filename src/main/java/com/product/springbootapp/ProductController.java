package com.product.springbootapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @GetMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

}

