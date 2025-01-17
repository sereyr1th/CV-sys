package com.cvmaker.cvmaker.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ContentDisposition;
import org.springframework.web.bind.annotation.*;
import com.cvmaker.cvmaker.service.ResumeService;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/resumes")
public class ResumeController {
    private final ResumeService resumeService;
    
    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }
    
    @PostMapping("/template")
    public ResponseEntity<Map<String, Object>> saveTemplate(@RequestBody Map<String, String> templateData) {
        Long resumeId = resumeService.saveTemplate(templateData.get("selectedTemplate"));
        Map<String, Object> response = new HashMap<>();
        response.put("id", resumeId);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/generate-pdf/{id}")
    public ResponseEntity<byte[]> generatePdf(@PathVariable Long id) {
        byte[] pdfContent = resumeService.generatePdf(id);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                .filename("resume.pdf").build());
        
        return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
    }
}