package com.cvmaker.cvmaker.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cvmaker.cvmaker.entity.Resume;
import com.cvmaker.cvmaker.repository.ResumeRepository;

@Service
public class ResumeService {

    private final ResumeRepository resumeRepository;

    public ResumeService(ResumeRepository resumeRepository) {
        this.resumeRepository = resumeRepository;
    }

    public List<Resume> getAllResumes() {
        return resumeRepository.findAll();
    }

    public Resume saveResume(Resume resume) {
        return resumeRepository.save(resume);
    }
}