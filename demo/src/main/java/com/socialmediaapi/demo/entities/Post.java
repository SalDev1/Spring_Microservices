package com.socialmediaapi.demo.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.socialmediaapi.demo.entities.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    /*
    *  We will map a post to a user
    * */
    @Id
    @GeneratedValue
    private Integer id;

//    @Size(min = 10)
    private String description;

    // Many Posts --> One User.
    // If you want to fetch the details of the post and the user in
    // the same query , then you are asking for eager fetch.

    /*
    *  If you fetch post , but don't really want user details , then
    *  go for LAZY Fetch.
    * */
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;
}
