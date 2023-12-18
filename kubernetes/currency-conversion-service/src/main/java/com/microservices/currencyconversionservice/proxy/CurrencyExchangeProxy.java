package com.microservices.currencyconversionservice.proxy;


import com.microservices.currencyconversionservice.entites.CurrencyConversion;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;


// Currency Exchange Proxy is been created over here. (A specific instance)
//@FeignClient(name="currency-exchange", url="localhost:8000")

// To distribute the load among various multiple instances
/*
*   We want feign client to talk to eureka and pick up
*   the instances of currency exchange and do load balancing between
*   them.
* */
@FeignClient(name = "currency-exchange" , url = "${CURRENCY_EXCHANGE_SERVICE_HOST:http://localhost}:8000")
//@FeignClient(name = "currency-exchange", url = "${CURRENCY_EXCHANGE_URI:http://localhost}:8000")
public interface CurrencyExchangeProxy {

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    // We are creating a structure similar in the currency exchange.
    public CurrencyConversion retrieveExchangeValue(
            @PathVariable String from, @PathVariable String to);
}