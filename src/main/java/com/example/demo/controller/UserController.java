package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.example.demo.validation.ValidationGroups;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile/me")
    public String myProfile(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User currentUser = userService.findUserByEmail(userDetails.getUsername());
        return "redirect:/users/profile/" + currentUser.getId();
    }

    @GetMapping("/profile/{userId}")
    public String viewProfile(@PathVariable UUID userId, Model model, Authentication authentication) {
        try {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User profileUser = userService.findUserById(userId);
            model.addAttribute("user", profileUser);
            model.addAttribute("isOwnProfile", userDetails.getUsername().equals(profileUser.getEmail()));
            return "user/profile";
        } catch (Exception e) {
            return "redirect:/";
        }
    }

    @PostMapping("/profile/{userId}/upload-avatar")
    public String uploadAvatar(@PathVariable UUID userId, @RequestParam("avatar") MultipartFile file, Authentication authentication, RedirectAttributes redirectAttributes) {
        if (!isOwner(authentication, userId)) return "redirect:/error/403";
        try {
            userService.updateUserAvatar(userId, file);
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật avatar thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/users/profile/" + userId;
    }

    @GetMapping("/profile/{userId}/edit")
    public String showEditProfileForm(@PathVariable UUID userId, Model model, Authentication authentication) {
        if (!isOwner(authentication, userId)) return "error/403";
        model.addAttribute("user", userService.findUserById(userId));
        return "user/edit-profile";
    }

    @PostMapping("/profile/{userId}/update")
    public String updateProfile(
            @PathVariable UUID userId,
            @Validated(ValidationGroups.OnUpdate.class) @ModelAttribute("user") User user,
            BindingResult bindingResult,
            Authentication authentication,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (!isOwner(authentication, userId)) return "redirect:/error/403";

        if (bindingResult.hasErrors()) {
            user.setId(userId); // Giữ lại ID để action của form không bị lỗi
            return "user/edit-profile";
        }

        try {
            userService.updateUserProfile(userId, user);
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật thông tin thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra: " + e.getMessage());
        }
        return "redirect:/users/profile/" + userId;
    }

    private boolean isOwner(Authentication authentication, UUID userId) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User currentUser = userService.findUserByEmail(userDetails.getUsername());
        return currentUser.getId().equals(userId);
    }
}
