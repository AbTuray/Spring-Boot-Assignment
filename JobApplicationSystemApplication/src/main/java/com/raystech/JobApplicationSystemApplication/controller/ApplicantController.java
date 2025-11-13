package com.raystech.JobApplicationSystemApplication.controller;

import com.raystech.JobApplicationSystemApplication.entity.Applicant;
import com.raystech.JobApplicationSystemApplication.service.ApplicantManager;
import com.raystech.JobApplicationSystemApplication.service.JobManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ApplicantController {

    @Autowired
    private ApplicantManager service;

    @Autowired
    private JobManager jobService;

//    @GetMapping("/applicants")
//    public String listApplicants(Model model, @RequestParam(required = false) String keyword) {
//        if (keyword != null && !keyword.isEmpty()) {
//            model.addAttribute("applicants", service.search(keyword));
//        } else {
//            model.addAttribute("applicants", service.findAll());
//        }
//        return "applicants";
//    }

    @GetMapping("/applicants")
    public String listApplicants(Model model, @RequestParam(required = false) String keyword) {
        if (keyword != null && !keyword.isEmpty()) {
            model.addAttribute("applicants", service.search(keyword));
        } else {
            model.addAttribute("applicants", service.findAll());
        }
        model.addAttribute("jobs", jobService.findAll()); // Add this
        return "applicants";
    }

    @GetMapping("/applicants/new")
    public String newApplicantForm(Model model) {
        model.addAttribute("applicant", new Applicant());
        return "applicant-form";
    }

    @PostMapping("/applicants")
    public String saveApplicant(@ModelAttribute Applicant applicant) {
        service.save(applicant);
        return "redirect:/applicants";
    }

    @GetMapping("/applicants/edit/{id}")
    public String editApplicantForm(@PathVariable Long id, Model model) {
        model.addAttribute("applicant", service.findById(id).orElseThrow());
        return "applicant-form";
    }

    @GetMapping("/applicants/delete/{id}")
    public String deleteApplicant(@PathVariable Long id) {
        service.deleteById(id);
        return "redirect:/applicants";
    }
}
