package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

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
} 