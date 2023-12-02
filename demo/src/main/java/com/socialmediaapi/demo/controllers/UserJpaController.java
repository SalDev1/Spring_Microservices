package com.socialmediaapi.demo.controllers;

import com.socialmediaapi.demo.entities.Post;
import com.socialmediaapi.demo.entities.User;
import com.socialmediaapi.demo.entities.UserNotFoundException;
import com.socialmediaapi.demo.repository.PostRepository;
import com.socialmediaapi.demo.repository.UserRepository;
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
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserJpaController {
    // Component is managed by spring , so autowired it.
    @Autowired
    public UserDaoService userService;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public PostRepository postRepository;

    // GET /users
    @RequestMapping(method = RequestMethod.GET , path = "/jpa/users")
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }

    // GET /users/{id}

    // Learning About HATEOUS -> EntityModel , WebMvcLinkBuilder
    @GetMapping("/jpa/users/{id}")
    public EntityModel<User> fetchUser(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);

        if(user.isEmpty())
            throw new UserNotFoundException("id : " + id);

        // We are adding links to this entityModel.
        EntityModel<User> entityModel = EntityModel.of(user.get());

        // Linking a specific method using linkTo() method and attaching links to the
        // retrieve all users link
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(link.withRel("all-users"));

        return entityModel;
    }

    // Create a User
    // Using the responseEntity you are able to get the
    // response of your kind of request you make.
    @PostMapping("/jpa/users")
    public ResponseEntity<User> createUser(@RequestBody User user)  {
        User savedUser = userRepository.save(user);
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
    @DeleteMapping("/jpa/users/{id}")
    public void deleteUser(@PathVariable int id) {
        userRepository.deleteById(id);
    }

    // Creating REST API for Posts.

    // Retrieve all the posts using the user id.
    @GetMapping("/jpa/users/{id}/posts")
    public List<Post> fetchPostsForUser(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty())
            throw new UserNotFoundException("id : " + id);
        return user.get().getPosts();
    }

    // Create a post based on the user's id.
    @PostMapping("/jpa/users/{id}/posts")
    public ResponseEntity<Post> createPostsForUser(@PathVariable int id , @Valid @RequestBody Post new_post) {
        // Find the user by id
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty())
            throw new UserNotFoundException("id : " + id);

        new_post.setUser(user.get());
        // Also save it in the post repository.
        Post savedPost = postRepository.save(new_post);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
