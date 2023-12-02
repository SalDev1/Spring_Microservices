package com.socialmediaapi.demo.controllers;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping(method = RequestMethod.GET , path = "/hello-world")
    public String helloWorld() {
        return "Hello World";
    }
}


/*
*  GET --> Retrieve Details of a resource
*  POST --> Create a new resource
*  PUT --> Update an existing resource
*  PATCH --> Update part of a resource.
* DELETE --> DELETE a resource.
* */