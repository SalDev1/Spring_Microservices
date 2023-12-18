package com.microservices.currencyexchangeservice.repository;

import com.microservices.currencyexchangeservice.entites.CurrencyExchange;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyExchangeRepository
        extends JpaRepository<CurrencyExchange , Long> {
    CurrencyExchange findByFromAndTo(String from , String to);
}
