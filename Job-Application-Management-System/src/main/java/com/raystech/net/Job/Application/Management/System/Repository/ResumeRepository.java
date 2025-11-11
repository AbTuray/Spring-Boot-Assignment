package com.raystech.net.Job.Application.Management.System.Repository;

import com.raystech.net.Job.Application.Management.System.Entity.Applicant;
import com.raystech.net.Job.Application.Management.System.Entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
    Optional<Resume> findByApplicant(Applicant applicant);
}
