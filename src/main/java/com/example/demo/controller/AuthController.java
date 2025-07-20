package com.example.demo.controller;

// <--- REMOVE THESE IMPORTS if they are no longer used in this class
// import com.example.demo.model.User;
// import com.example.demo.service.UserService;
// import jakarta.servlet.http.HttpSession;
// import lombok.AllArgsConstructor;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.example.demo.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
// <--- REMOVE THESE IMPORTS
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestParam;

@Controller
// <--- REMOVE @AllArgsConstructor if you no longer have UserService injected here
public class AuthController {
    // <--- REMOVE UserService injection if you are no longer doing manual login checks
    // UserService userService;

    @Autowired
    UserService userService;

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

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    @PostMapping("/register")
    public String processRegistration(
            @Validated(ValidationGroups.OnRegister.class) @ModelAttribute("user") User user,
            BindingResult bindingResult,
            @RequestParam(required = false) MultipartFile avatar,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "auth/register";
        }
        try {
            userService.registerUser(user, avatar);
            redirectAttributes.addFlashAttribute("successMessage", "Đăng ký thành công! Vui lòng đăng nhập.");
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            bindingResult.rejectValue("email", "email.exists", e.getMessage());
            return "auth/register";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Đã có lỗi không mong muốn xảy ra. Vui lòng thử lại.");
            return "auth/register";
        }
    }
}