package com.cvmaker.cvmaker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.cvmaker.cvmaker.dto.UserDto;

@Controller
public class ViewController {

    @GetMapping("/register-page")
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new UserDto());
        return "register";
    }
}