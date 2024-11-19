package com.marcykiewicz.mateusz.joba_application_portal.service;

import com.marcykiewicz.mateusz.joba_application_portal.entity.JobPostActivity;
import com.marcykiewicz.mateusz.joba_application_portal.entity.dto.RecruiterPostedJob;
import com.marcykiewicz.mateusz.joba_application_portal.entity.dto.RecruiterPostedJobDto;
import com.marcykiewicz.mateusz.joba_application_portal.entity.dto.RecruiterPostedJobMapper;
import com.marcykiewicz.mateusz.joba_application_portal.repository.JobPostActivityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class JobPostActivityService {

    private final JobPostActivityRepository jobPostActivityRepository;
    private final RecruiterPostedJobMapper recruiterPostedJobMapper;

    @Transactional
    public JobPostActivity save(JobPostActivity jobPostActivity) {
       return jobPostActivityRepository.save(jobPostActivity);
    }

    public List<RecruiterPostedJobDto> getRecruiterPostedJobs(Long recruiterId) {

        List<RecruiterPostedJob> jobEntities = jobPostActivityRepository.getRecruiterPostedJobs(recruiterId);

        List<RecruiterPostedJobDto> jobDtos = new ArrayList<>();

        jobEntities.forEach(entity ->
                jobDtos.add(recruiterPostedJobMapper.mapToDto(entity)));

        return jobDtos;
    }

}
