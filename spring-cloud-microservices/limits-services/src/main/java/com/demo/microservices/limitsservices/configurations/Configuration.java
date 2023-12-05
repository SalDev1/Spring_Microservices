package com.demo.microservices.limitsservices.configurations;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


// This annotation helps us to add with the
// configuration properties.
@Component
@ConfigurationProperties("limits-service")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Configuration {
    // We create a configuration class that matches the structure
    // within the application.properties.
    private int maximum;
    private int minimum;
}
