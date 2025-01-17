package com.cvmaker.cvmaker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ResumeViewController {

    @GetMapping("/resume-maker")
    public String resumeMakerPage() {
        return "resume-maker"; // Ensure a corresponding 'resume-maker.html' exists in the 'templates' directory
    }
}
