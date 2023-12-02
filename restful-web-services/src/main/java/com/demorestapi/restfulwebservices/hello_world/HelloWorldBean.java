package com.demorestapi.restfulwebservices.hello_world;


import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class HelloWorldBean {

    public HelloWorldBean(String message) {
        this.message = message;
    }
    private String message;
    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
    @Override
    public String toString() {
        return "HelloWorldBean [message =" + message + "]";
    }
}
