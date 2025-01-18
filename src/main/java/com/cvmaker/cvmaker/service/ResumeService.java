package com.cvmaker.cvmaker.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.cvmaker.cvmaker.entity.Resume;
import com.cvmaker.cvmaker.repository.ResumeRepository;

@Service
public class ResumeService {
    private static final Logger logger = LoggerFactory.getLogger(ResumeService.class);
    private final ResumeRepository resumeRepository;
    
    public ResumeService(ResumeRepository resumeRepository) {
        this.resumeRepository = resumeRepository;
    }

    public Resume getCurrentResume() {
        Resume resume = new Resume();
        return resumeRepository.save(resume);
    }
    
    public Long saveTemplate(String selectedTemplate) {
        Resume resume = getCurrentResume();
        resume.setSelectedTemplate(selectedTemplate);
        Resume savedResume = resumeRepository.save(resume);
        return savedResume.getId();
    }
    
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

    public byte[] generatePdfFromFormData(Map<String, String> formData) {
        String templateHtml = getTemplateHtml("resume-template"); // Use the new template
        String filledTemplate = fillTemplateWithFormData(templateHtml, formData);
        
        return convertHtmlToPdf(filledTemplate);
    }

    public String generateHtmlFromFormData(Map<String, String> formData) {
        String templateHtml = getTemplateHtml("resume-template"); // Use the new template
        return fillTemplateWithFormData(templateHtml, formData);
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
            .replace("{{fullName}}", resume.getFullName() != null ? resume.getFullName() : "Full Name")
            .replace("{{email}}", resume.getEmail() != null ? resume.getEmail() : "email@example.com")
            .replace("{{phone}}", resume.getPhone() != null ? resume.getPhone() : "123-456-7890")
            .replace("{{address}}", resume.getAddress() != null ? resume.getAddress() : "123 Main St, Anytown, USA")
            .replace("{{education}}", resume.getEducation() != null ? resume.getEducation() : "Your education details here")
            .replace("{{workExperience}}", resume.getWorkExperience() != null ? resume.getWorkExperience() : "Your work experience details here");
    }

    private String fillTemplateWithFormData(String template, Map<String, String> formData) {
        StringBuilder educationBuilder = new StringBuilder();
        StringBuilder workExperienceBuilder = new StringBuilder();

        // Assuming education and work experience entries are indexed
        int index = 0;
        while (formData.containsKey("institution[" + index + "]")) {
            educationBuilder.append("<p>")
                .append(formData.get("institution[" + index + "]")).append(", ")
                .append(formData.get("degree[" + index + "]")).append(", ")
                .append(formData.get("eduStartDate[" + index + "]")).append(" - ")
                .append(formData.get("eduEndDate[" + index + "]"))
                .append("</p>");
            index++;
        }

        index = 0;
        while (formData.containsKey("company[" + index + "]")) {
            workExperienceBuilder.append("<p>")
                .append(formData.get("company[" + index + "]")).append(", ")
                .append(formData.get("position[" + index + "]")).append(", ")
                .append(formData.get("workStartDate[" + index + "]")).append(" - ")
                .append(formData.get("workEndDate[" + index + "]")).append("<br>")
                .append(formData.get("description[" + index + "]"))
                .append("</p>");
            index++;
        }

        return template
            .replace("{{fullName}}", formData.getOrDefault("fullName", "Full Name"))
            .replace("{{email}}", formData.getOrDefault("email", "email@example.com"))
            .replace("{{phone}}", formData.getOrDefault("phone", "123-456-7890"))
            .replace("{{address}}", formData.getOrDefault("address", "123 Main St, Anytown, USA"))
            .replace("{{education}}", educationBuilder.toString())
            .replace("{{workExperience}}", workExperienceBuilder.toString());
    }
    
    private byte[] convertHtmlToPdf(String html) {
        try {
            logger.info("Generated HTML for PDF: {}", html); // Log the generated HTML
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