package com.demo.microservices.limitsservices.controllers;


import com.demo.microservices.limitsservices.beans.Limits;
import com.demo.microservices.limitsservices.configurations.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitsController {

    @Autowired
    private Configuration configuration;

    @GetMapping("/limits")
    // We have the simple limit service up and running.
    public Limits retriveLimits() {
//        return new Limits(1,1000);
        return new Limits(configuration.getMinimum() , configuration.getMaximum());
    }

    // Demo REST API.
//    @GetMapping("/hello-world")
//    public String helloWorld() {
//        return "Hello World";
//    }
}
