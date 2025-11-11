package com.raystech.net.Job.Application.Management.System.Service;

import com.raystech.net.Job.Application.Management.System.Entity.Applicant;
import com.raystech.net.Job.Application.Management.System.Repository.ApplicantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicantManager implements ApplicantService {

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Applicant registerApplicant(Applicant applicant) {
        if (applicantRepository.findByEmail(applicant.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }
        applicant.setPassword(passwordEncoder.encode(applicant.getPassword()));
        return applicantRepository.save(applicant);
    }

    public Optional<Applicant> findByEmail(String email) {
        return applicantRepository.findByEmail(email);
    }

    public List<Applicant> getAllApplicants(){
        return applicantRepository.findAll();
    }

    public Optional<Applicant> findById(Long id){
        return applicantRepository.findById(id);
    }
}
