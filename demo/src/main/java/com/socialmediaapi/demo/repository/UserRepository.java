package com.socialmediaapi.demo.repository;

import com.socialmediaapi.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;


// This entire file is necessary as to have
// direct communication with the H2 / MySQL Database and this is achieved
// by extending JpaRepository to an interface.
public interface UserRepository extends JpaRepository<User , Integer> {
}
