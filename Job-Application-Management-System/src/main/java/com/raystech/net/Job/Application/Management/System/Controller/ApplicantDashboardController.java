package com.raystech.net.Job.Application.Management.System.Controller;

import com.raystech.net.Job.Application.Management.System.Entity.Applicant;
import com.raystech.net.Job.Application.Management.System.Entity.Application;
import com.raystech.net.Job.Application.Management.System.Entity.Job;
import com.raystech.net.Job.Application.Management.System.Service.ApplicantManager;
import com.raystech.net.Job.Application.Management.System.Service.ApplicationManager;
import com.raystech.net.Job.Application.Management.System.Service.JobManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/applicant")
public class ApplicantDashboardController {

    @Autowired
    private JobManager jobManager;

    @Autowired
    private ApplicantManager applicantManager;

    @Autowired
    private ApplicationManager applicationManager;

    @GetMapping("/applicant/dashboard")
    public String viewDashboard(Model model) {
        model.addAttribute("jobs", jobManager.getAllJobs());
        return "applicant/dashboard"; // dashboard.html
    }

    @GetMapping("/apply/{jobId}")
    public String applyForJob(@PathVariable Long jobId,
                              @AuthenticationPrincipal User user,
                              RedirectAttributes redirectAttributes) {
        try {
            Optional<Applicant> applicantOpt = applicantManager.findByEmail(user.getUsername());
            Optional<Job> jobOpt = jobManager.getJobById(jobId);

            if (applicantOpt.isPresent() && jobOpt.isPresent()) {
                Application application = new Application();
                application.setApplicant(applicantOpt.get());
                application.setJob(jobOpt.get());
                application.setDateApplied(LocalDate.now());
                application.setStatus("Pending");

                applicationManager.saveApplication(application);

                redirectAttributes.addFlashAttribute("success", "You have successfully applied for the job!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Error: Applicant or Job not found!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error applying for job: " + e.getMessage());
        }

        return "redirect:/applicant/dashboard";
    }

    @GetMapping("/applications")
    public String viewMyApplications(@AuthenticationPrincipal User user, Model model) {
        Optional<Applicant> applicantOpt = applicantManager.findByEmail(user.getUsername());
        if (applicantOpt.isPresent()) {
            model.addAttribute("applications",
                    applicationManager.getApplicationsByApplicant(applicantOpt.get()));
        }
        return "applicant/my_applications"; // my_applications.html
    }


}
