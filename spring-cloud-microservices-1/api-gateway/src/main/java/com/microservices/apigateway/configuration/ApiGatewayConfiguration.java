package com.microservices.apigateway.configuration;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class ApiGatewayConfiguration {
    // We are trying to create a custom router for us.
    // http:localhost:8765/get (Getting a response back with few headers).
    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
        // Creating a routeFunction ,if the request comes to /get , redirect us to the
        // given url.

//        Function<PredicateSpec, Buildable<Route>> routeFunction =
//                p -> p.path("/get")
//                        .filters(f -> f
//                                .addRequestHeader("MyHeader" , "MyURI")
//                                .addRequestParameter("Param" , "MyValue"))
//                        .uri("http://httpbin.org:80");

        // Configuring the routes to make them better.
        return builder.routes().route(p -> p.path("/get")
                .filters(f -> f
                        .addRequestHeader("MyHeader" , "MyURI")
                        .addRequestParameter("Param" , "MyValue"))
                .uri("http://httpbin.org:80"))
                .route(p -> p.path("/currency-exchange/**")
                        .uri("lb://currency-exchange"))
                .route(p -> p.path("/currency-conversion/**")
                        .uri("lb://currency-conversion"))
                .route(p -> p.path("/currency-conversion-feign/**")
                        .uri("lb://currency-conversion"))
                .route(p -> p.path("/currency-conversion-new/**")
                        .filters(f ->
                                f.rewritePath("/currency-conversion-new/(?<segment>.*)" ,
                                        "/currency-conversion-feign/${segment}"))
                        .uri("lb://currency-conversion"))
                .build();
    }
}
