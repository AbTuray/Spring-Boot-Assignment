package com.raystech.net.Job.Application.Management.System.Controller;

import com.raystech.net.Job.Application.Management.System.Entity.Applicant;
import com.raystech.net.Job.Application.Management.System.Entity.Application;
import com.raystech.net.Job.Application.Management.System.Entity.Job;
import com.raystech.net.Job.Application.Management.System.Repository.ApplicantRepository;
import com.raystech.net.Job.Application.Management.System.Repository.ApplicationRepository;
import com.raystech.net.Job.Application.Management.System.Repository.JobRepository;
import com.raystech.net.Job.Application.Management.System.Service.ApplicantManager;
import com.raystech.net.Job.Application.Management.System.Service.JobManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
@RequestMapping("/applicant")
public class DashboardController {

    @Autowired
    private final ApplicantRepository applicantRepository;
    @Autowired
    private final JobRepository jobRepository;
    @Autowired
    private final ApplicationRepository applicationRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public DashboardController(ApplicantRepository applicantRepository, JobRepository jobRepository,
                               ApplicationRepository applicationRepository) {
        this.applicantRepository = applicantRepository;
        this.jobRepository = jobRepository;
        this.applicationRepository = applicationRepository;
    }

    // 🔹 Dashboard page
    @GetMapping("/dashboard")
    public String dashboard(Authentication auth, Model model) {
        String email = auth.getName();
        Applicant applicant = applicantRepository.findByEmail(email).orElse(null);
        if (applicant == null) return "redirect:/login";

        List<Job> jobs = jobRepository.findAll();
        List<Application> applications = applicationRepository.findByApplicant(applicant);

        model.addAttribute("applicant", applicant);
        model.addAttribute("jobs", jobs);
        model.addAttribute("applications", applications);
        return "dashboard";
    }

    // 🔹 Apply for a job (with resume upload)
    @PostMapping("/apply/{jobId}")
    public String applyToJob(@PathVariable Long jobId,
                             @RequestParam("resume") MultipartFile resumeFile,
                             Authentication auth,
                             Model model) {
        String email = auth.getName();
        Applicant applicant = applicantRepository.findByEmail(email).orElse(null);
        if (applicant == null) return "redirect:/login";

        Job job = jobRepository.findById(jobId).orElse(null);
        if (job == null) {
            model.addAttribute("error", "Job not found!");
            return "redirect:/applicant/dashboard";
        }

        // Check if already applied
        if (applicationRepository.existsByApplicantAndJob(applicant, job)) {
            model.addAttribute("error", "You already applied for this job!");
            return "redirect:/applicant/dashboard";
        }

        // Upload resume
        String resumePath = null;
        if (!resumeFile.isEmpty()) {
            try {
                String fileName = applicant.getFullName().replaceAll("\\s+", "_")
                        + "_" + job.getTitle().replaceAll("\\s+", "_")
                        + "_" + System.currentTimeMillis()
                        + "_" + resumeFile.getOriginalFilename();

                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) Files.createDirectories(uploadPath);

                Path filePath = uploadPath.resolve(fileName);
                Files.copy(resumeFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                resumePath = fileName;
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("error", "Error uploading resume.");
                return "redirect:/applicant/dashboard";
            }
        }

        // Save application
        Application application = new Application();
        application.setApplicant(applicant);
        application.setJob(job);
        application.setStatus("Pending");
        application.setResumePath(resumePath);
        applicationRepository.save(application);

        return "redirect:/applicant/dashboard";
    }

    // 🔹 Download Resume
    @GetMapping("/download/{appId}")
    @ResponseBody
    public ResponseEntity<Resource> downloadResume(@PathVariable Long appId) throws IOException {
        Application application = applicationRepository.findById(appId).orElse(null);
        if (application == null || application.getResumePath() == null)
            return ResponseEntity.notFound().build();

        Path filePath = Paths.get(uploadDir).resolve(application.getResumePath());
        Resource resource = new UrlResource(filePath.toUri());
        if (!resource.exists()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
