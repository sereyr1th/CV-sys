package com.cvmaker.cvmaker.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String education;
    private String workExperience;
    private String selectedTemplate;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getEducation() { return education; }
    public void setEducation(String education) { this.education = education; }

    public String getWorkExperience() { return workExperience; }
    public void setWorkExperience(String workExperience) { this.workExperience = workExperience; }

    
    public String getSelectedTemplate() {
        return selectedTemplate;
    }
    
    public void setSelectedTemplate(String selectedTemplate) {
        this.selectedTemplate = selectedTemplate;
    }
}
