package com.raystech.JobApplicationSystemApplication.controller;

import com.raystech.JobApplicationSystemApplication.entity.Applicant;
import com.raystech.JobApplicationSystemApplication.entity.Resume;
import com.raystech.JobApplicationSystemApplication.service.ApplicantManager;
import com.raystech.JobApplicationSystemApplication.service.ResumeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ResumeController {
    @Autowired
    private ResumeManager service;

    @Autowired
    private ApplicantManager applicantService;

    @GetMapping("/resumes")
    public String listResumes(Model model) {
        model.addAttribute("resumes", service.findAll());
        return "resumes";
    }

    // Form for adding/updating resume for a specific applicant
    @GetMapping("/applicants/{applicantId}/resume")
    public String resumeForm(@PathVariable Long applicantId, Model model) {
        Applicant applicant = applicantService.findById(applicantId).orElseThrow();
        Resume resume = applicant.getResume() != null ? applicant.getResume() : new Resume();
        model.addAttribute("resume", resume);
        model.addAttribute("applicantId", applicantId);
        return "resume-form";
    }

    @PostMapping("/applicants/{applicantId}/resume")
    public String saveResume(@PathVariable Long applicantId, @RequestParam String content) {
        service.addResumeToApplicant(applicantId, content);
        return "redirect:/applicants";
    }
}
