package com.example.clotheswarehouse.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalUserAdvice {

    @ModelAttribute
    public void addUserToModel(Model model, @AuthenticationPrincipal UserDetails user) {
        if (user != null) {
            model.addAttribute("username", user.getUsername());
        }
    }
}
