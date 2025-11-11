package com.raystech.net.Job.Application.Management.System.Service;

import com.raystech.net.Job.Application.Management.System.Entity.Applicant;
import com.raystech.net.Job.Application.Management.System.Entity.Application;
import com.raystech.net.Job.Application.Management.System.Repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ApplicationManager implements ApplicationService{

    @Autowired
    private ApplicationRepository applicationRepository;

    public Application saveApplication(Application application) {
        try {
            return applicationRepository.save(application);
        } catch (Exception e) {
            throw new RuntimeException("Error saving application: " + e.getMessage());
        }
    }

    public List<Application> getApplicationsByApplicant(Applicant applicant) {
        return applicationRepository.findByApplicant(applicant);
    }
}
