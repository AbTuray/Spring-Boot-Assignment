package com.raystech.JobApplicationSystemApplication.service;

import com.raystech.JobApplicationSystemApplication.entity.Applicant;
import com.raystech.JobApplicationSystemApplication.entity.Resume;

import java.util.List;
import java.util.Optional;

public interface ResumeService {

    public List<Resume> findAll();

    public Optional<Resume> findById(Long id);

    public Resume save(Resume resume);

    // Add/update resume for applicant
    public void addResumeToApplicant(Long applicantId, String content);
}
