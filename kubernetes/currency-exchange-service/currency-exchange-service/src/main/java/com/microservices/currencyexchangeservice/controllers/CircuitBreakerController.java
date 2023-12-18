package com.microservices.currencyexchangeservice.controllers;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


// We are exploring circuit breaker features.
@RestController
public class CircuitBreakerController {
    private Logger logger =
            LoggerFactory.getLogger(CircuitBreakerController.class);

    // Retry Framework
    @GetMapping("/sample-api")
  //  @CircuitBreaker(name = "default" , fallbackMethod = "hardcodedResponse")
//    @Retry(name = "sample-api",fallbackMethod = "hardcodedResponse") // This will try itself three times ,
    // And if the retry fails to do it three times, then it sends a
    // response back.
//    @RateLimiter(name="default")
    // 10s --> 10000 class to the sample api.
    @Bulkhead(name = "default")
    public String sampleApi() {
        logger.info("Sample API Call Received");
        ResponseEntity<String> forEntity = new RestTemplate()
                .getForEntity("http://localhost:8080/some-dummy-url"
                ,null,String.class);

        return forEntity.getBody();
    }

    // If a Retry framework tries multiple times for a response from the url
    // and stills fails to get something , it returns a fallback method.
    public String hardcodedResponse(Exception ex) {
        return "fallback-response";
    }
}
