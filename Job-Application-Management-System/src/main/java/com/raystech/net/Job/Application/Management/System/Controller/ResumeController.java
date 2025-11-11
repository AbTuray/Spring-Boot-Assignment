package com.raystech.net.Job.Application.Management.System.Controller;

import com.raystech.net.Job.Application.Management.System.Entity.Applicant;
import com.raystech.net.Job.Application.Management.System.Entity.Resume;
import com.raystech.net.Job.Application.Management.System.Service.ApplicantManager;
import com.raystech.net.Job.Application.Management.System.Service.ResumeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Controller
@RequestMapping("/applicant/resume")
public class ResumeController {

    @Autowired
    private ResumeManager resumeManager;

    @Autowired
    private ApplicantManager applicantManager;
    @GetMapping
    public String viewResumePage(@AuthenticationPrincipal User user, Model model) {
        Optional<Applicant> applicantOpt = applicantManager.findByEmail(user.getUsername());
        if (applicantOpt.isPresent()) {
            Optional<Resume> resumeOpt = resumeManager.getResumeByApplicant(applicantOpt.get());
            model.addAttribute("resume", resumeOpt.orElse(new Resume()));
        }
        return "applicant/resume";
    }

    @PostMapping("/upload")
    public String uploadResume(@RequestParam("file") MultipartFile file,
                               @RequestParam("skills") String skills,
                               @RequestParam("experience") String experience,
                               @AuthenticationPrincipal User user,
                               RedirectAttributes redirectAttributes) {
        try {
            Optional<Applicant> applicantOpt = applicantManager.findByEmail(user.getUsername());
            if (applicantOpt.isPresent()) {
                resumeManager.saveResume(file, skills, experience, applicantOpt.get());
                redirectAttributes.addFlashAttribute("success", "Resume uploaded successfully!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Applicant not found!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/applicant/resume";
    }

    @GetMapping("/download")
    @ResponseBody
    public Resource downloadResume(@AuthenticationPrincipal User user) throws MalformedURLException {
        Optional<Applicant> applicantOpt = applicantManager.findByEmail(user.getUsername());
        if (applicantOpt.isPresent()) {
            Optional<Resume> resumeOpt = resumeManager.getResumeByApplicant(applicantOpt.get());
            if (resumeOpt.isPresent()) {
                Path file = Paths.get(resumeOpt.get().getFileName());
                return new UrlResource(file.toUri());
            }
        }
        return null;
    }
}
