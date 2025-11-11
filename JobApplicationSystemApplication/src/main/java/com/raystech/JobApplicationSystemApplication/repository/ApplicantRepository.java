package com.raystech.JobApplicationSystemApplication.repository;

import com.raystech.JobApplicationSystemApplication.entity.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApplicantRepository extends JpaRepository<Applicant, Long> {
    @Query("SELECT a FROM Applicant a WHERE a.name LIKE %?1% OR a.email LIKE %?1%")
    List<Applicant> search(String keyword);
}
