package com.cvmaker.cvmaker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cvmaker.cvmaker.entity.Resume;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
}