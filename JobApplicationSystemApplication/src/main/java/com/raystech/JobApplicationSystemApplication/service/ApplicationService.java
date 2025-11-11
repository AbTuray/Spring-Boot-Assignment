package com.raystech.JobApplicationSystemApplication.service;

import com.raystech.JobApplicationSystemApplication.entity.Applicant;
import com.raystech.JobApplicationSystemApplication.entity.Application;
import com.raystech.JobApplicationSystemApplication.entity.Job;

import java.util.List;
import java.util.Optional;

public interface ApplicationService {
    public List<Application> findAll();

    public Optional<Application> findById(Long id);


    public Application save(Application application);

    public void deleteById(Long id);

    // Add application to applicant and job
    public Application addApplicationToApplicantAndJob(Long applicantId, Long jobId, String status);
}
