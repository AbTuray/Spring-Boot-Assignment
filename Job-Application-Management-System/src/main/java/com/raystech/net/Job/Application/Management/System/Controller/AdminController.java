package com.raystech.net.Job.Application.Management.System.Controller;

import com.raystech.net.Job.Application.Management.System.Entity.Application;
import com.raystech.net.Job.Application.Management.System.Entity.Job;
import com.raystech.net.Job.Application.Management.System.Repository.ApplicationRepository;
import com.raystech.net.Job.Application.Management.System.Repository.JobRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class AdminController {

    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public AdminController(ApplicationRepository applicationRepository, JobRepository jobRepository) {
        this.applicationRepository = applicationRepository;
        this.jobRepository = jobRepository;
    }

    // 🔹 Admin Dashboard
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<Application> applications = applicationRepository.findAll();
        List<Job> jobs = jobRepository.findAll();

        model.addAttribute("applications", applications);
        model.addAttribute("jobs", jobs);
        return "admin-dashboard";
    }

    // 🔹 Update Application Status
    @PostMapping("/updateStatus/{id}")
    public String updateStatus(@PathVariable Long id, @RequestParam("status") String status) {
        Application app = applicationRepository.findById(id).orElse(null);
        if (app != null) {
            app.setStatus(status);
            applicationRepository.save(app);
        }
        return "redirect:/admin/dashboard";
    }

    // 🔹 Download Applicant Resume
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

    // 🔹 Delete a Job
    @GetMapping("/deleteJob/{id}")
    public String deleteJob(@PathVariable Long id) {
        jobRepository.deleteById(id);
        return "redirect:/admin/dashboard";
    }
}
