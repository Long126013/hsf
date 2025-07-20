package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // <--- ADD THIS FIELD
    private final CloudinaryService cloudinaryService;

    @Value("${cloudinary.default_avatar_url}")
    private String defaultAvatarUrl;

    // <--- ADD THIS CONSTRUCTOR
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, CloudinaryService cloudinaryService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.cloudinaryService = cloudinaryService;
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

    @Transactional
    public User registerUser(User user, MultipartFile avatarFile) throws IOException {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email đã được sử dụng.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (avatarFile != null && !avatarFile.isEmpty()) {
            user.setAvatarUrl(cloudinaryService.uploadAvatar(avatarFile));
        } else {
            user.setAvatarUrl(defaultAvatarUrl);
        }
        return userRepository.save(user);
    }

    @Transactional
    public User updateUserProfile(UUID userId, User userUpdateData) {
        User existingUser = findUserById(userId);
        existingUser.setFullName(userUpdateData.getFullName());
        return userRepository.save(existingUser);
    }

    @Transactional
    public User updateUserAvatar(UUID userId, MultipartFile avatarFile) throws IOException {
        User user = findUserById(userId);
        String avatarUrl = cloudinaryService.uploadAvatar(avatarFile);
        user.setAvatarUrl(avatarUrl);
        return userRepository.save(user);
    }

    public User findUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy người dùng với ID: " + userId));
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy người dùng với email: " + email));
    }
}
