package com.smartwallet.service;

import com.smartwallet.model.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private final Map<String, User> userByEmail = new HashMap<>();

    public User register(String name, String email, String password) {
        if (userByEmail.containsKey(email)) {
            throw new RuntimeException("User already exists");
        }

        User user = new User(name, email, password);
        userByEmail.put(email, user);
        return user;
    }

    public User login(String email, String password) {
        if (!userByEmail.containsKey(email)) {
            throw new RuntimeException("Wrong credentials, please check again");
        }

        User user = userByEmail.get(email);

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Wrong credentials, please check again");
        }

        return user;
    }
}
