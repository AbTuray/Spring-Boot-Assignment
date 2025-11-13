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

//    @GetMapping("/jobs")
    @RequestMapping(value = "jobs", method = RequestMethod.GET)
    public String listJobs(Model model) {
        model.addAttribute("jobs", service.findAll());
        model.addAttribute("applicants", applicantService.findAll()); // Add this
        return "jobs";
    }

//    @GetMapping("/jobs/new")
    @RequestMapping(value = "jobs/new", method = RequestMethod.GET)
    public String newJobForm(Model model) {
        model.addAttribute("job", new Job());
        return "job-form";
    }

//    @PostMapping("/jobs")
    @RequestMapping(value = "jobs", method = RequestMethod.POST)
    public String saveJob(@ModelAttribute Job job) {
        service.save(job);
        return "redirect:/jobs";
    }

//    @GetMapping("/jobs/edit/{id}")
    @RequestMapping(value = "jobs/edit/{id}", method = RequestMethod.GET)
    public String editJobForm(@PathVariable Long id, Model model) {
        model.addAttribute("job", service.findById(id).orElseThrow());
        return "job-form";
    }

//    @GetMapping("/jobs/delete/{id}")
    @RequestMapping(value = "jobs/delete/{id}", method = RequestMethod.GET)
    public String deleteJob(@PathVariable Long id) {
        service.deleteById(id);
        return "redirect:/jobs";
    }

    // Add applicant to job
//    @PostMapping("/jobs/{jobId}/add-applicant")
    @RequestMapping(value = "jobs/{jobId}/add-applicant", method = RequestMethod.POST)
    public String addApplicantToJob(@PathVariable Long jobId, @RequestParam Long applicantId, @RequestParam String status) {
        service.addApplicantToJob(jobId, applicantId, status);
        return "redirect:/jobs";
    }
}
