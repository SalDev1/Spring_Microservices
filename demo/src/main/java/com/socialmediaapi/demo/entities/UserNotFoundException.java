package com.socialmediaapi.demo.entities;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Creating a proper error response structure for every resource that doesn't exist
// in our application.
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException{
   public UserNotFoundException(String message) {
       // It passes the message to the super class (RuntimeException).
       super(message);
   }
}
