package com.socialmediaapi.demo.repository;

import com.socialmediaapi.demo.entities.Post;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post , Integer> {
}
