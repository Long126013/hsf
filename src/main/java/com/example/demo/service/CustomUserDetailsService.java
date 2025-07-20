package com.example.demo.service; // Or com.example.demo.security or similar

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList; // Used for simple authorities list. For real apps, map roles.

@Service // Mark this as a Spring component for auto-detection
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Find your application's User by the provided email (which acts as the username)
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // Convert your application's User object into Spring Security's UserDetails.
        // The password returned here MUST be the BCrypt encoded password from your database.
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),          // The "username" for Spring Security (your user's email)
                user.getPassword(),       // The BCrypt encoded password retrieved from the DB
                new ArrayList<>()         // A list of authorities (roles/permissions). Empty for now.
                // Example: List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}