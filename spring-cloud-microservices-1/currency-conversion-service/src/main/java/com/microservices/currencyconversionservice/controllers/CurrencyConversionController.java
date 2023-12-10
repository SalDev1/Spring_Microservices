package com.microservices.currencyconversionservice.controllers;


import com.microservices.currencyconversionservice.entites.CurrencyConversion;
import com.microservices.currencyconversionservice.proxy.CurrencyExchangeProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

@RestController
public class CurrencyConversionController {

    @Autowired
    private CurrencyExchangeProxy proxy;

    // http://localhost:8100/currency-conversion/from/USD/to/INR/quantity/10.
    @GetMapping("/currency-conversion/from/{currency_from}/to/{currency_to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversion(@PathVariable String currency_from, @PathVariable String currency_to  , @PathVariable BigDecimal quantity) {

        // Allowing the Currency Conversion sends a request to the Currency Exchange Service and
        // get a response from it.
        HashMap<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from" , currency_from);
        uriVariables.put("to" , currency_to);

        ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate().getForEntity
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
        CurrencyConversion currencyConversion = proxy.retrieveExchangeValue(currency_from,currency_to);

        return new CurrencyConversion(currencyConversion.getId(),
                currency_from,currency_to,
                currencyConversion.getConversionMultiple(),
                quantity,
                quantity.multiply(currencyConversion.getConversionMultiple()),
                currencyConversion.getEnvironment() +  " " + "feign");
    }
}
