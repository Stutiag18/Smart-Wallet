package com.smartwallet.service;

import com.smartwallet.model.User;
import com.smartwallet.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User register(String name, String email, String password) {

        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("User already exists");
        }

        User user = new User(name, email, password);

        // ✅ THIS LINE SAVES TO POSTGRES
        return userRepository.save(user);
    }

    public User login(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Wrong credentials"));

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Wrong credentials");
        }

        return user;
    }
}
