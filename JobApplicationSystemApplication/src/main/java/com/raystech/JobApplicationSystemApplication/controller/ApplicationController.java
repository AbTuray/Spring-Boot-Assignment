package com.raystech.JobApplicationSystemApplication.controller;

import com.raystech.JobApplicationSystemApplication.entity.Applicant;
import com.raystech.JobApplicationSystemApplication.entity.Application;
import com.raystech.JobApplicationSystemApplication.entity.Job;
import com.raystech.JobApplicationSystemApplication.service.ApplicantService;
import com.raystech.JobApplicationSystemApplication.service.ApplicationManager;
import com.raystech.JobApplicationSystemApplication.service.ApplicationService;
import com.raystech.JobApplicationSystemApplication.service.JobManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ApplicationController {

    @Autowired
    private ApplicationManager service;

    @Autowired
    private ApplicantService applicantService;

    @Autowired
    private JobManager jobService;

    @GetMapping("/applications")
    public String listApplications(Model model) {
        model.addAttribute("applications", service.findAll());
        return "applications";
    }

    @GetMapping("/applications/new")
    public String newApplicationForm(Model model) {
        model.addAttribute("application", new Application());
        model.addAttribute("applicants", applicantService.findAll());
        model.addAttribute("jobs", jobService.findAll());
        return "application-form";
    }

    @PostMapping("/applications")
    public String saveApplication(@ModelAttribute Application application, @RequestParam Long applicantId, @RequestParam Long jobId) {
        Applicant applicant = applicantService.findById(applicantId).orElseThrow();
        Job job = jobService.findById(jobId).orElseThrow();
        application.setApplicant(applicant);
        application.setJob(job);
        service.save(application);
        return "redirect:/applications";
    }

    @GetMapping("/applications/edit/{id}")
    public String editApplicationForm(@PathVariable Long id, Model model) {
        model.addAttribute("application", service.findById(id).orElseThrow());
        model.addAttribute("applicants", applicantService.findAll());
        model.addAttribute("jobs", jobService.findAll());
        return "application-form";
    }

    @GetMapping("/applications/delete/{id}")
    public String deleteApplication(@PathVariable Long id) {
        service.deleteById(id);
        return "redirect:/applications";
    }

    // Endpoint for adding application to applicant (from applicant page, e.g.)
    @PostMapping("/applicants/{applicantId}/add-application")
    public String addApplicationToApplicant(@PathVariable Long applicantId, @RequestParam Long jobId, @RequestParam String status) {
        service.addApplicationToApplicantAndJob(applicantId, jobId, status);
        return "redirect:/applicants";
    }
}
