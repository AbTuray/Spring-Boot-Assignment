package com.raystech.net.Job.Application.Management.System.Service;

import com.raystech.net.Job.Application.Management.System.Entity.Job;

import java.util.List;
import java.util.Optional;

public interface JobService {

    public List<Job> getAllJobs();
    public Optional<Job> getJobById(Long id);
    public Job saveJob(Job job);
    public void deleteJob(Long id);

}
