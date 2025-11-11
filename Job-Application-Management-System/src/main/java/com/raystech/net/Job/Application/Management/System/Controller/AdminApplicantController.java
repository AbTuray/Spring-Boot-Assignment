package com.raystech.net.Job.Application.Management.System.Controller;

import com.raystech.net.Job.Application.Management.System.Entity.Applicant;
import com.raystech.net.Job.Application.Management.System.Entity.Resume;
import com.raystech.net.Job.Application.Management.System.Service.ApplicantManager;
import com.raystech.net.Job.Application.Management.System.Service.ResumeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/applicants")
public class AdminApplicantController {

    @Autowired
    private ApplicantManager applicantManager;

    @Autowired
    private ResumeManager resumeManager;

    // ✅ Display list of all applicants
    @GetMapping
    public String viewAllApplicants(Model model) {
        List<Applicant> applicants = applicantManager.getAllApplicants();
        model.addAttribute("applicants", applicants);
        return "admin/applicant_list";
    }

    // ✅ View detailed info for one applicant
    @GetMapping("/{id}")
    public String viewApplicantDetails(@PathVariable Long id, Model model) {
        Optional<Applicant> applicantOpt = applicantManager.findById(id);
        if (applicantOpt.isPresent()) {
            Applicant applicant = applicantOpt.get();
            Optional<Resume> resumeOpt = resumeManager.getResumeByApplicant(applicant);

            model.addAttribute("applicant", applicant);
            model.addAttribute("resume", resumeOpt.orElse(null));
            return "admin/applicant_details";
        }
        return "redirect:/admin/applicants";
    }

    // ✅ Allow admin to download applicant resume
    @GetMapping("/{id}/resume/download")
    @ResponseBody
    public Resource downloadApplicantResume(@PathVariable Long id) throws MalformedURLException {
        Optional<Applicant> applicantOpt = applicantManager.findById(id);
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
