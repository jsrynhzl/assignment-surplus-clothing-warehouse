package com.example.clotheswarehouse.controller;

import java.util.EnumSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.clotheswarehouse.model.Clothing;
import com.example.clotheswarehouse.model.Clothing.Brand;
import com.example.clotheswarehouse.model.User;
import com.example.clotheswarehouse.repository.ClothingRepository;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/form")
public class FormController {

    @Autowired
    private ClothingRepository clothingRepository;

    @GetMapping
    public String form() {
        return "form";
    }

    @ModelAttribute
    public void brands(Model model) {
        var brands = EnumSet.allOf(Brand.class);
        model.addAttribute("brands", brands);
    }

    @ModelAttribute()
    public Clothing clothing() {
        return Clothing.builder().build();
    }

    @PostMapping
    public String processClothingAddition(@Valid Clothing clothing,
                                          BindingResult result,
                                          @AuthenticationPrincipal User user) {
        if (result.hasErrors()) {
            return "form";
        }

        log.info("Processing clothing: {}", clothing);

        clothingRepository.save(clothing);
        log.info("Clothing added to warehouse inventory: {}", clothing);

        if (user != null && "ADMIN".equals(user.getRole())) {
            return "redirect:/summary/admin";
        } else {
            return "redirect:/summary";
        }
    }
}
