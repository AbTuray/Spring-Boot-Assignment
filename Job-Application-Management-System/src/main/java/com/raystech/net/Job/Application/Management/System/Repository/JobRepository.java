package com.raystech.net.Job.Application.Management.System.Repository;

import com.raystech.net.Job.Application.Management.System.Entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {

}
