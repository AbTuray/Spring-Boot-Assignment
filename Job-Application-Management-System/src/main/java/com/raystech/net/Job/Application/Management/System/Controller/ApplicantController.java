package com.raystech.net.Job.Application.Management.System.Controller;

import com.raystech.net.Job.Application.Management.System.Entity.Applicant;
import com.raystech.net.Job.Application.Management.System.Service.ApplicantManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ApplicantController {

    @Autowired
    private ApplicantManager applicantManager;

//    @GetMapping("/register")
    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String showRegistrationForm(Model model) {
        model.addAttribute("applicant", new Applicant());
        return "register";
    }

//    @PostMapping("/register")
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String registerApplicant(@ModelAttribute Applicant applicant, Model model) {
        try {
            applicantManager.registerApplicant(applicant);
            model.addAttribute("success", "Registration successful — please log in.");
            return "login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

//    @GetMapping("/login")
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String showLoginForm() {
        return "login";
    }

//    @GetMapping("/dashboard")
    @RequestMapping(value = "dashboard", method = RequestMethod.GET)
    public String dashboard() {
        return "dashboard";
    }
}
