package com.microservices.currencyconversionservice.controllers;


import com.microservices.currencyconversionservice.entites.CurrencyConversion;
import com.microservices.currencyconversionservice.proxy.CurrencyExchangeProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

@Configuration(proxyBeanMethods = false)
class RestTemplateConfiguration {
    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}

@RestController
public class CurrencyConversionController {
    private Logger logger = LoggerFactory.getLogger(CurrencyConversionController.class);
    @Autowired
    private CurrencyExchangeProxy proxy;

    @Autowired
    private RestTemplate restTemplate;

    // http://localhost:8100/currency-conversion/from/USD/to/INR/quantity/10.
    @GetMapping("/currency-conversion/from/{currency_from}/to/{currency_to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversion(@PathVariable String currency_from, @PathVariable String currency_to  , @PathVariable BigDecimal quantity) {

        //CHANGE-KUBERNETES
        logger.info("calculateCurrencyConversion called with {} to {} with {}", currency_from, currency_to, quantity);

        // Allowing the Currency Conversion sends a request to the Currency Exchange Service and
        // get a response from it.
        HashMap<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from" , currency_from);
        uriVariables.put("to" , currency_to);

//        ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate().getForEntity
        ResponseEntity<CurrencyConversion> responseEntity = restTemplate.getForEntity
                ("http://localhost:8000/currency-exchange/from/{from}/to/{to}",
                        CurrencyConversion.class, uriVariables);
        // The response is shown after getting it from currency conversion.
        CurrencyConversion currencyConversion = responseEntity.getBody();

        return new CurrencyConversion(currencyConversion.getId(),
                currency_from,currency_to,
                currencyConversion.getConversionMultiple(),
                quantity,
                quantity.multiply(currencyConversion.getConversionMultiple()),
                currencyConversion.getEnvironment() +  " " + "rest-template");
    }
    // Same Method but with feign
    // http://localhost:8100/currency-conversion-feign/from/AUD/to/INR/quantity/10
    @GetMapping("/currency-conversion-feign/from/{currency_from}/to/{currency_to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversionFeign(@PathVariable String currency_from, @PathVariable String currency_to  , @PathVariable BigDecimal quantity) {
        //CHANGE-KUBERNETES
        logger.info("calculateCurrencyConversionFeign called with {} to {} with {}", currency_from, currency_to, quantity);

        CurrencyConversion currencyConversion = proxy.retrieveExchangeValue(currency_from,currency_to);

        return new CurrencyConversion(currencyConversion.getId(),
                currency_from,currency_to,
                currencyConversion.getConversionMultiple(),
                quantity,
                quantity.multiply(currencyConversion.getConversionMultiple()),
                currencyConversion.getEnvironment() +  " " + "feign");
    }
}
