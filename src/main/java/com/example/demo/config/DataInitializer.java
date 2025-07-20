package com.example.demo.config;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class DataInitializer implements CommandLineRunner {

    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        // <--- IMPORTANT CHANGE: Use actual email format for usernames and plain-text passwords
        userService.createUser(new User("admin@example.com", "admin"));
        userService.createUser(new User("test@example.com", "test"));
        userService.createUser(new User("user@example.com", "user"));
        userService.createUser(new User("ngoctrinh@example.com", "null"));
        userService.createUser(new User("t","t"));

        System.out.println("Initialized users with encoded passwords!");
    }
}