package com.raystech.JobApplicationSystemApplication.repository;

import com.raystech.JobApplicationSystemApplication.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
