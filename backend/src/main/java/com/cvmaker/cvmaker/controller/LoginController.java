package com.cvmaker.cvmaker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    
    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // This matches login.html in the templates folder
    }
}