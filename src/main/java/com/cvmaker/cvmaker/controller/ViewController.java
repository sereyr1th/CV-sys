package com.cvmaker.cvmaker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    
    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }
}