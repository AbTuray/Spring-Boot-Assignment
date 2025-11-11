package com.raystech.net.Job.Application.Management.System.Controller;

import com.raystech.net.Job.Application.Management.System.Entity.Job;
import com.raystech.net.Job.Application.Management.System.Service.JobManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/jobs")
public class JobController {

    @Autowired
    private JobManager jobManager;

    @GetMapping
    public String listJobs(Model model) {
        model.addAttribute("jobs", jobManager.getAllJobs());
        return "admin/jobs"; // jobs.html
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("job", new Job());
        return "admin/job_form"; // job_form.html
    }

    @PostMapping("/save")
    public String saveJob(@ModelAttribute("job") Job job, RedirectAttributes redirectAttributes) {
        try {
            jobManager.saveJob(job);
            redirectAttributes.addFlashAttribute("success", "Job saved successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error saving job: " + e.getMessage());
        }
        return "redirect:/admin/jobs";
    }

    @GetMapping("/edit/{id}")
    public String editJob(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return jobManager.getJobById(id)
                .map(job -> {
                    model.addAttribute("job", job);
                    return "admin/job_form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Job not found!");
                    return "redirect:/admin/jobs";
                });
    }

    @GetMapping("/delete/{id}")
    public String deleteJob(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            jobManager.deleteJob(id);
            redirectAttributes.addFlashAttribute("success", "Job deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting job: " + e.getMessage());
        }
        return "redirect:/admin/jobs";
    }
}
