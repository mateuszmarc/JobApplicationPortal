package com.marcykiewicz.mateusz.joba_application_portal.service;

import com.marcykiewicz.mateusz.joba_application_portal.entity.JobSeekerProfile;
import com.marcykiewicz.mateusz.joba_application_portal.exception.DatabaseIntegrityException;
import com.marcykiewicz.mateusz.joba_application_portal.repository.JobSeekerProfileRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JobSeekerProfileService {

    private final JobSeekerProfileRepository jobSeekerProfileRepository;

    public JobSeekerProfile findById(Long id) {

        return jobSeekerProfileRepository.findById(id).orElseThrow(() ->
                new DatabaseIntegrityException("There is no such JobSeekerProfile"));
    }

    @Transactional
    public JobSeekerProfile save(JobSeekerProfile jobSeekerProfile) {
        return jobSeekerProfileRepository.save(jobSeekerProfile);
    }
}
