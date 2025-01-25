package com.cvmaker.cvmaker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cvmaker.cvmaker.dto.UserDto;
import com.cvmaker.cvmaker.service.UserService;

@Controller
public class AdminPageController {

    private final UserService userService;

    public AdminPageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/register")
    public String showAdminRegisterForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "register-admin";
    }

    @PostMapping("/admin/register")
    public String registerAdmin(UserDto userDto, Model model) {
        try {
            userService.registerAdmin(userDto);
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register-admin";
        }
    }
}