package com.raystech.JobApplicationSystemApplication.service;

import com.raystech.JobApplicationSystemApplication.entity.Job;
import com.raystech.JobApplicationSystemApplication.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobManager implements JobService{
    @Autowired
    private JobRepository repository;

    @Autowired
    @Lazy
    private ApplicationManager applicationService;

    public List<Job> findAll() {
        return repository.findAll();
    }

    public Optional<Job> findById(Long id) {
        return repository.findById(id);
    }

    public Job save(Job job) {
        return repository.save(job);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    // Add applicant to job (creates application)
    public void addApplicantToJob(Long jobId, Long applicantId, String status) {
        applicationService.addApplicationToApplicantAndJob(applicantId, jobId, status);
    }
}
