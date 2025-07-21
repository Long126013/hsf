package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class TopUpController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/topup")
    public String showTopUpPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userRepository.findByEmail(email).orElse(null);
        if (user != null) {
            model.addAttribute("userId", user.getId());
        }
        return "topup";
    }

    @PostMapping("/demo/topup-balance")
    public RedirectView demoTopUpBalance(@AuthenticationPrincipal UserDetails userDetails, @RequestParam long amount) {
        String email = userDetails.getUsername();
        User user = userRepository.findByEmail(email).orElse(null);
        if (user != null) {
            user.setBalance(user.getBalance() + amount);
            userRepository.save(user);
        }
        return new RedirectView("/payment-success");
    }
} 