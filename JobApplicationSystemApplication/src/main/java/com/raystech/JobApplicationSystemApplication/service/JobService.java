package com.raystech.JobApplicationSystemApplication.service;

import com.raystech.JobApplicationSystemApplication.entity.Job;

import java.util.List;
import java.util.Optional;

public interface JobService {
    public List<Job> findAll();

    public Optional<Job> findById(Long id);

    public Job save(Job job);

    public void deleteById(Long id);

    // Add applicant to job (creates application)
    public void addApplicantToJob(Long jobId, Long applicantId, String status);
}
