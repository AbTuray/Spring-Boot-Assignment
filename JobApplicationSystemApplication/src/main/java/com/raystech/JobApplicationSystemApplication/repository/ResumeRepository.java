package com.raystech.JobApplicationSystemApplication.repository;

import com.raystech.JobApplicationSystemApplication.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
}
