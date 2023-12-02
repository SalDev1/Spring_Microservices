package com.socialmediaapi.demo.services;


import com.socialmediaapi.demo.entities.User;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
public class UserDaoService {
    // JPA / Hibernate to communicate with the Database (H2 Database)
    // Right now , we are working with static users.

    // public List<User> findAll()
    private static List<User> users = new ArrayList<>();

    private static int usersCount = 0;

    // Static Users.
    static {
//        users.add(new User(++usersCount,"Adam" , LocalDate.now().minusYears(30)));
//        users.add(new User(++usersCount,"Eve" , LocalDate.now().minusYears(25)));
//        users.add(new User(++usersCount,"Jim" , LocalDate.now().minusYears(21)));
    }

    public List<User> findAll() {
        return users;
    }
    // public User save(User user)
    public User save(User user) {
        user.setId(++usersCount);
        users.add(user);
        return user;
    }

    // Find out the specific user based on the id.
    public User findOne(int id) {
       // Using Functional Programming;
//        for(User u : users) {
//            if(u.getId() == id) {
//                return u;
//            }
//        }
//        return null;
        Predicate<? super User> predicate = user -> user.getId() == id;
        return users.stream().filter(predicate).findFirst().orElse(null);
    }

    public void deleteById(int id) {
        Predicate<? super User> predicate = user -> user.getId() == id;
        users.removeIf(predicate);
    }
}
