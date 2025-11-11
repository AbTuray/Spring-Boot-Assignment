package com.raystech.JobApplicationSystemApplication.controller;

import com.raystech.JobApplicationSystemApplication.entity.Job;
import com.raystech.JobApplicationSystemApplication.service.ApplicantManager;
import com.raystech.JobApplicationSystemApplication.service.JobManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class JobController {

    @Autowired
    private JobManager service;

    @Autowired
    private ApplicantManager applicantService;

    @GetMapping("/jobs")
    public String listJobs(Model model) {
        model.addAttribute("jobs", service.findAll());
        return "jobs";
    }

    @GetMapping("/jobs/new")
    public String newJobForm(Model model) {
        model.addAttribute("job", new Job());
        return "job-form";
    }

    @PostMapping("/jobs")
    public String saveJob(@ModelAttribute Job job) {
        service.save(job);
        return "redirect:/jobs";
    }

    @GetMapping("/jobs/edit/{id}")
    public String editJobForm(@PathVariable Long id, Model model) {
        model.addAttribute("job", service.findById(id).orElseThrow());
        return "job-form";
    }

    @GetMapping("/jobs/delete/{id}")
    public String deleteJob(@PathVariable Long id) {
        service.deleteById(id);
        return "redirect:/jobs";
    }

    // Add applicant to job
    @PostMapping("/jobs/{jobId}/add-applicant")
    public String addApplicantToJob(@PathVariable Long jobId, @RequestParam Long applicantId, @RequestParam String status) {
        service.addApplicantToJob(jobId, applicantId, status);
        return "redirect:/jobs";
    }
}
