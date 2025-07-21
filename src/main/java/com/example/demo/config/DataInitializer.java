package com.example.demo.config;

import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class DataInitializer implements CommandLineRunner {

    private UserService userService;
    private ProductService productService;

    @Override
    public void run(String... args) throws Exception {
        // <--- IMPORTANT CHANGE: Use actual email format for usernames and plain-text passwords

        userInit();
        productInit();
    }

    public void userInit(){
        try{
            userService.createUser(new User("admin@example.com", "admin", 0));
            userService.createUser(new User("test@example.com", "test", 0));
            userService.createUser(new User("user@example.com", "user", 0));
            userService.createUser(new User("ngoctrinh@example.com", "null", 0));
            userService.createUser(new User("t","t", 0));

            log.info("User initialization complete");
        } catch(Exception ex){
            ex.printStackTrace();
            log.info("User initialization failed", ex.getMessage());
        }
    }

    public void productInit(){
        try{
            Product product1 = new Product("Cardboard Box", 100, 20);
            Product product2 = new Product("Grenade (fake)", 50, 200);
            Product product3 = new Product("Samsung Galaxy", 1, 10_000);
            Product product4 = new Product("Pirated Minecraft", 4, 10);

            productService.createProduct(product1);
            productService.createProduct(product2);
            productService.createProduct(product3);
            productService.createProduct(product4);

            log.info("Product initialization complete");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error while initializing product",e.getMessage());
        }
    }
}