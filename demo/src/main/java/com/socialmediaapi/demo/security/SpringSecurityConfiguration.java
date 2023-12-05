package com.socialmediaapi.demo.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;


// The main goal is overwriting a default filter chain defined by spring
// security.
@Configuration
public class SpringSecurityConfiguration {

    // Define a filter chain method.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        /*
        *  1] All requests should be authenticated
        *  2] If a request is not authenticated , a web page is shown.
        *  3] CSRF --> POST , PUT.
        * */

        // 1] All requests should be authenticated.
        http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());

        // 2] We want to enable REST API from basic authentication.
        http.httpBasic(Customizer.withDefaults());

        // 3] Disable CSRF --> POST , PUT.
        http.csrf(csrf -> csrf.disable());
        return http.build();
    }
}
