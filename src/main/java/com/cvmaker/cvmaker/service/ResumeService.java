package com.cvmaker.cvmaker.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.cvmaker.cvmaker.entity.Resume;
import com.cvmaker.cvmaker.repository.ResumeRepository;

@Service
public class ResumeService {
    private final ResumeRepository resumeRepository;
    
    public ResumeService(ResumeRepository resumeRepository) {
        this.resumeRepository = resumeRepository;
    }

    // Add this method that was missing
    public Resume getCurrentResume() {
        // This is a simplified version. You might want to implement session handling
        // or other logic to track the current resume being created
        Resume resume = new Resume();
        return resumeRepository.save(resume);
    }
    
    public Long saveTemplate(String selectedTemplate) {
        Resume resume = getCurrentResume();
        resume.setSelectedTemplate(selectedTemplate);
        Resume savedResume = resumeRepository.save(resume);
        return savedResume.getId();
    }
    
    // Add this method that was missing
    public Resume getResumeById(Long id) {
        return resumeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resume not found with id: " + id));
    }
    
    public byte[] generatePdf(Long id) {
        Resume resume = getResumeById(id);
        String templateHtml = getTemplateHtml(resume.getSelectedTemplate());
        String filledTemplate = fillTemplateWithData(templateHtml, resume);
        
        return convertHtmlToPdf(filledTemplate);
    }
    
    private String getTemplateHtml(String templateName) {
        try {
            Resource resource = new ClassPathResource("templates/" + templateName + ".html");
            return new String(FileCopyUtils.copyToByteArray(resource.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load template", e);
        }
    }
    
    private String fillTemplateWithData(String template, Resume resume) {
        return template
            .replace("{{fullName}}", resume.getFullName())
            .replace("{{email}}", resume.getEmail())
            .replace("{{phone}}", resume.getPhone())
            .replace("{{address}}", resume.getAddress())
            .replace("{{education}}", resume.getEducation())
            .replace("{{workExperience}}", resume.getWorkExperience());
    }
    
    private byte[] convertHtmlToPdf(String html) {
        try {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            renderer.layout();
            
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            renderer.createPDF(os);
            return os.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF", e);
        }
    }
}