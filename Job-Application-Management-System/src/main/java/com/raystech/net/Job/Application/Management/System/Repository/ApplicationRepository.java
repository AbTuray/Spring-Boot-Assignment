package com.raystech.net.Job.Application.Management.System.Repository;

import com.raystech.net.Job.Application.Management.System.Entity.Applicant;
import com.raystech.net.Job.Application.Management.System.Entity.Application;
import com.raystech.net.Job.Application.Management.System.Entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByApplicant(Applicant applicant);

    boolean existsByApplicantAndJob(Applicant applicant, Job job);
}
