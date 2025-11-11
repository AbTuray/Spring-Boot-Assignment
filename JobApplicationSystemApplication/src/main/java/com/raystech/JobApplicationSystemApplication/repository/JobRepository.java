package com.raystech.JobApplicationSystemApplication.repository;

import com.raystech.JobApplicationSystemApplication.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
}
