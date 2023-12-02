package com.socialmediaapi.demo.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity(name = "user_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    // Adding Validations to the parameters.
    @Id
    @GeneratedValue
    private int id;

    @Size(min = 2 , message = "Name should have at least 2 characters")
    @JsonProperty("user_name")
    private String name;

    @Past(message = "Birth Date should be in the past")
    private LocalDate birthDate;

    // One User --> Many Posts.
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Post> posts;
}
