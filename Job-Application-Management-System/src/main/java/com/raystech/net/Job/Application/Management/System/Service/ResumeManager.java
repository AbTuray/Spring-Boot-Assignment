package com.raystech.net.Job.Application.Management.System.Service;

import com.raystech.net.Job.Application.Management.System.Entity.Applicant;
import com.raystech.net.Job.Application.Management.System.Entity.Resume;
import com.raystech.net.Job.Application.Management.System.Repository.ResumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
@Service
public class ResumeManager implements ResumeService{
    private static final String UPLOAD_DIR = "uploads/resumes/";

    @Autowired
    private ResumeRepository resumeRepository;
    public Resume saveResume(MultipartFile file, String skills, String experience, Applicant applicant) {
        try {
            // Create uploads directory if not exist
            File dir = new File(UPLOAD_DIR);
            if (!dir.exists()) dir.mkdirs();

            // Save file
            String filePath = UPLOAD_DIR + file.getOriginalFilename();
            Path path = Paths.get(filePath);
            Files.write(path, file.getBytes());

            // Check if applicant already has a resume
            Optional<Resume> existingResume = resumeRepository.findByApplicant(applicant);
            Resume resume = existingResume.orElse(new Resume());
            resume.setApplicant(applicant);
            resume.setSkills(skills);
            resume.setExperience(experience);
            resume.setFileName(filePath);

            return resumeRepository.save(resume);

        } catch (IOException e) {
            throw new RuntimeException("Error saving resume: " + e.getMessage());
        }
    }

    public Optional<Resume> getResumeByApplicant(Applicant applicant) {
        return resumeRepository.findByApplicant(applicant);
    }

}
