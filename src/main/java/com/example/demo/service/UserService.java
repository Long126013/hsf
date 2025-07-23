package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder; // <--- ADD THIS IMPORT
import org.springframework.stereotype.Service;

import java.util.Optional; // <--- ADD THIS IMPORT
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // <--- ADD THIS FIELD

    // <--- ADD THIS CONSTRUCTOR
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public long getBalance(UUID userId) {
        return userRepository.findById(userId)
                .map(User::getBalance)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public void setBalance(UUID userId, long newBalance) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setBalance(newBalance);
        userRepository.save(user);
    }


    public void increaseBalance(UUID userId, long amount) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setBalance(user.getBalance() + amount);
        userRepository.save(user);
    }

    public void decreaseBalance(UUID userId, long amount) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (user.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient balance");
        }
        user.setBalance(user.getBalance() - amount);
        userRepository.save(user);
    }

    public User createUser(User user) {
        // <--- IMPORTANT CHANGE: ENCODE THE PASSWORD BEFORE SAVING
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }
}