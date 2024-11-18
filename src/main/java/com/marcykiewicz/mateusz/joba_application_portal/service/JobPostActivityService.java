package com.marcykiewicz.mateusz.joba_application_portal.service;

import com.marcykiewicz.mateusz.joba_application_portal.entity.JobPostActivity;
import com.marcykiewicz.mateusz.joba_application_portal.repository.JobPostActivityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JobPostActivityService {
    private final JobPostActivityRepository jobPostActivityRepository;

    @Transactional
    public JobPostActivity save(JobPostActivity jobPostActivity) {
       return jobPostActivityRepository.save(jobPostActivity);
    }
}
