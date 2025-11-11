package com.raystech.JobApplicationSystemApplication.service;

import com.raystech.JobApplicationSystemApplication.entity.Applicant;
import com.raystech.JobApplicationSystemApplication.entity.Resume;
import com.raystech.JobApplicationSystemApplication.repository.ResumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ResumeManager implements ResumeService {

    @Autowired
    private ResumeRepository repository;

    @Autowired
    private ApplicantService applicantService;

    public List<Resume> findAll() {
        return repository.findAll();
    }

    public Optional<Resume> findById(Long id) {
        return repository.findById(id);
    }

    public Resume save(Resume resume) {
        return repository.save(resume);
    }

    // Add/update resume for applicant
    public void addResumeToApplicant(Long applicantId, String content) {
        Applicant applicant = applicantService.findById(applicantId).orElseThrow();
        Resume resume = applicant.getResume();
        if (resume == null) {
            resume = new Resume();
        }
        resume.setContent(content);
        resume.setApplicant(applicant);
        save(resume);
        applicant.setResume(resume);
        applicantService.save(applicant);
    }
}
