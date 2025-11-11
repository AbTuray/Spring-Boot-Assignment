package com.raystech.net.Job.Application.Management.System.Service;

import com.raystech.net.Job.Application.Management.System.Entity.Applicant;
import com.raystech.net.Job.Application.Management.System.Entity.Application;

import java.util.List;

public interface ApplicationService {

    public Application saveApplication(Application application);

    public List<Application> getApplicationsByApplicant(Applicant applicant);
}
