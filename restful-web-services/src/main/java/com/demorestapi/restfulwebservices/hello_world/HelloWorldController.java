package com.demorestapi.restfulwebservices.hello_world;


import org.aspectj.bridge.Message;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
public class HelloWorldController {
    private MessageSource messageSource;
    // Mapping this request to a GET Method
    public HelloWorldController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @RequestMapping(method = RequestMethod.GET , path = "/hello-world")
    public String helloWorld() {
        return "Hello World";
    }


    // Enhancing the hello world to return a bean.
    @GetMapping(path = "/hello-world-bean")
    public HelloWorldBean helloWorldBean() {
        return new HelloWorldBean("Hello World Bean");
    }

    //Path Parameters
    // /users/{id} , /todos/{id} , --> /users/2/todos/200
    // The num variables like 2 or 200 is called path parameters.

    //hello-world/path-variable/{name}
    @GetMapping(path = "/hello-world/path-variable/{name}")
    public HelloWorldBean helloWorldPathVariable(@PathVariable String name) {
        return new HelloWorldBean(String.format("Hello World , %s" , name));
    }


    // 1 -> good.morning.message

    // The below rest api is for internationalization.
    // 1] Create message.properties for each specific language that exists.
    @GetMapping(path = "/hello-world-internationalized")
    public String helloWorldInternationalization() {
        // Getting the locale from the REQUEST Method
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage("good.morning.message" , null ,"Default Message" , locale);
//        return "Hello World V2";
    }
}

/*
*  1] Expose a REST API
*  2] Show "Hello World" using the resources.
*  3] To mark a controller to be useful for REST API , use the
*  annotation.
* */