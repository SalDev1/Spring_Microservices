package com.socialmediaapi.demo.controllers;

import com.socialmediaapi.demo.entities.UserNotFoundException;
import com.socialmediaapi.demo.entities.User;
import com.socialmediaapi.demo.services.UserDaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserController {

    // Component is managed by spring , so autowired it.
    @Autowired
    public UserDaoService userService;

    // GET /users
    @RequestMapping(method = RequestMethod.GET , path = "/users")
    public List<User> retrieveAllUsers() {
        return userService.findAll();
    }

    // GET /users/{id}

    // Learning About HATEOUS -> EntityModel , WebMvcLinkBuilder
    @GetMapping("/users/{id}")
    public EntityModel<User> fetchUser(@PathVariable int id) {
        User user = userService.findOne(id);

        if(user == null)
            throw new UserNotFoundException("id : " + id);

        // We are adding links to this entityModel.
        EntityModel<User> entityModel = EntityModel.of(user);

        // Linking a specific method using linkTo() method and attaching links to the
        // retrieve all users link
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(link.withRel("all-users"));

        return entityModel;
    }

    // Create a User
    // Using the responseEntity you are able to get the
    // response of your kind of request you make.
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user)  {
        User savedUser = userService.save(user);
//        return user;
        // /users/{id} , user.getId;

        // The below code adds a path /id to the current URL and
        // replace the id with the current savedUser id.

        // Eg :- http://localhost:8080/users/4
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    // Deleting a user from the list of users.
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
         userService.deleteById(id);
    }
}
