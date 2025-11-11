package com.raystech.JobApplicationSystemApplication.service;

import com.raystech.JobApplicationSystemApplication.entity.Applicant;
import com.raystech.JobApplicationSystemApplication.entity.Application;
import com.raystech.JobApplicationSystemApplication.entity.Job;
import com.raystech.JobApplicationSystemApplication.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicationManager {

    @Autowired
    private ApplicationRepository repository;

    @Autowired
    private ApplicantService applicantService;

    @Autowired
    private JobManager jobService;

    public List<Application> findAll() {
        return repository.findAll();
    }

    public Optional<Application> findById(Long id) {
        return repository.findById(id);
    }

    public Application save(Application application) {
        return repository.save(application);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    // Add application to applicant and job
    public Application addApplicationToApplicantAndJob(Long applicantId, Long jobId, String status) {
        Applicant applicant = applicantService.findById(applicantId).orElseThrow();
        Job job = jobService.findById(jobId).orElseThrow();
        Application app = new Application();
        app.setApplicant(applicant);
        app.setJob(job);
        app.setStatus(status);
        return save(app);
    }
}
