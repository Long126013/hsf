package com.example.demo.controller;

// <--- REMOVE THESE IMPORTS if they are no longer used in this class
// import com.example.demo.model.User;
// import com.example.demo.service.UserService;
// import jakarta.servlet.http.HttpSession;
// import lombok.AllArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
// <--- REMOVE THESE IMPORTS
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestParam;

@Controller
// <--- REMOVE @AllArgsConstructor if you no longer have UserService injected here
public class AuthController {
    // <--- REMOVE UserService injection if you are no longer doing manual login checks
    // UserService userService;

    @GetMapping({"/","/login"})
    public String login(){
        return "login"; // This maps to src/main/resources/templates/login.html
    }

    // <--- REMOVE THIS ENTIRE METHOD. Spring Security handles POST /login.
    // @PostMapping("/login")
    // public String login(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession session){
    //     User user = userService.checkLogin(email, password);
    //     if(user!=null){
    //         session.setAttribute("loggedInUser",user);
    //         return "redirect:/products";
    //     }
    //     return "login";
    // }

    // <--- ADD a simple controller for your /products page (your success URL)

}