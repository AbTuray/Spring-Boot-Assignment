package com.raystech.JobApplicationSystemApplication.service;

import com.raystech.JobApplicationSystemApplication.entity.Applicant;

import java.util.List;
import java.util.Optional;

public interface ApplicantService {

    public List<Applicant> findAll();

    public Optional<Applicant> findById(Long id);
    public Applicant save(Applicant applicant);

    public void deleteById(Long id);

    public List<Applicant> search(String keyword);
}
