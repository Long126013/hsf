package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder; // <--- ADD THIS IMPORT
import org.springframework.stereotype.Service;

import java.util.Optional; // <--- ADD THIS IMPORT

@Service
// Removed @AllArgsConstructor because we're explicitly defining a constructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // <--- ADD THIS FIELD

    // <--- ADD THIS CONSTRUCTOR
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(User user) {
        // <--- IMPORTANT CHANGE: ENCODE THE PASSWORD BEFORE SAVING
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // You can remove this method entirely, as Spring Security will handle login.
    // If you keep it for other non-Spring-Security logic, ensure it doesn't interfere.
    public User checkLogin(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // If you *were* to manually verify passwords (not recommended with Spring Security)
            // if (passwordEncoder.matches(password, user.getPassword())) {
            //     return user;
            // }
        }
        return null;
    }
}