package com.raystech.JobApplicationSystemApplication.service;

import com.raystech.JobApplicationSystemApplication.entity.Applicant;
import com.raystech.JobApplicationSystemApplication.repository.ApplicantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicantManager implements ApplicantService {

    @Autowired
    private ApplicantRepository repository;

    public List<Applicant> findAll() {
        return repository.findAll();
    }

    public Optional<Applicant> findById(Long id) {
        return repository.findById(id);
    }

    public Applicant save(Applicant applicant) {
        return repository.save(applicant);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public List<Applicant> search(String keyword) {
        return repository.search(keyword);
    }
}

