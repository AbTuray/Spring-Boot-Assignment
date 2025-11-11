package com.raystech.net.Job.Application.Management.System.Service;

import com.raystech.net.Job.Application.Management.System.Entity.Applicant;

import java.util.Optional;

public interface ApplicantService {

    Applicant registerApplicant(Applicant applicant);

    public Optional<Applicant> findByEmail(String email);

}
