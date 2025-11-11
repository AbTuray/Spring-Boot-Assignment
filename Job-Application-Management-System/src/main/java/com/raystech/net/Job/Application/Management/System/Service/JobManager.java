package com.raystech.net.Job.Application.Management.System.Service;

import com.raystech.net.Job.Application.Management.System.Entity.Job;
import com.raystech.net.Job.Application.Management.System.Repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobManager implements JobService{

    @Autowired
    private JobRepository jobRepository;

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    public Job saveJob(Job job) {
        try {
            return jobRepository.save(job);
        } catch (Exception e) {
            throw new RuntimeException("Error saving job: " + e.getMessage());
        }
    }

    public Optional<Job> getJobById(Long id) {
        return jobRepository.findById(id);
    }

    public void deleteJob(Long id) {
        try {
            jobRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting job: " + e.getMessage());
        }
    }
}
