package com.microservices.apigateway.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


// We are trying to create global filters over here.

@Component
public class LoggingFilter implements GlobalFilter {
    private Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    // Mainly helps us to fetch / filter out the request path executed by the user.
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // Needs some information about the request
        // --> use the exchange from ServerWebExchange
        logger.info("Path of the request received -> {}",
                exchange.getRequest().getPath());
        // We are letting the chain continue as it is.
        return chain.filter(exchange);
    }
}
