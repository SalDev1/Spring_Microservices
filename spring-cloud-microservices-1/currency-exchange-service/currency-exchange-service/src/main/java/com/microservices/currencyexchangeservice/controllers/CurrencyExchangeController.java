package com.microservices.currencyexchangeservice.controllers;


import com.microservices.currencyexchangeservice.entites.CurrencyExchange;
import com.microservices.currencyexchangeservice.repository.CurrencyExchangeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CurrencyExchangeController {
    private Logger logger = LoggerFactory.getLogger(CurrencyExchangeController.class);
    @Autowired
    private CurrencyExchangeRepository repository;
    // We get the value of the port by the inbuilt environment class.
    @Autowired
    private Environment environment;
    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveExchangeValue(@PathVariable String from , @PathVariable String to) {
//        CurrencyExchange curr =  new CurrencyExchange(10L,"USD","IND", BigDecimal.valueOf(50));

        logger.info("retrieveExchange called with {} to {}" , from  ,to);

        CurrencyExchange curr = repository.findByFromAndTo(from,to);
        if(curr == null) {
            throw new RuntimeException("Unable to Find Data for " + from + " to" + to);
        }

        String port = environment.getProperty("local.server.port");
        curr.setEnvironment(port);

        return curr;
    }
}
