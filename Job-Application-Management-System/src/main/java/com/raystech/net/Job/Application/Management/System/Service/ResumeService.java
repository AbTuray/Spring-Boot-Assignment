package com.raystech.net.Job.Application.Management.System.Service;

import com.raystech.net.Job.Application.Management.System.Entity.Applicant;
import com.raystech.net.Job.Application.Management.System.Entity.Resume;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface ResumeService {

    public Resume saveResume(MultipartFile file, String skills, String experience, Applicant applicant);

    public Optional<Resume> getResumeByApplicant(Applicant applicant);
}
