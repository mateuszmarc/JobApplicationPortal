package com.marcykiewicz.mateusz.joba_application_portal.entity.dto;

import com.marcykiewicz.mateusz.joba_application_portal.entity.JobCompany;
import com.marcykiewicz.mateusz.joba_application_portal.entity.JobLocation;
import org.springframework.stereotype.Service;

@Service
public class RecruiterPostedJobMapper {

    public RecruiterPostedJobDto mapToDto(RecruiterPostedJob recruiterPostedJob) {

        JobLocation jobLocation = new JobLocation(
                recruiterPostedJob.getLocationId(),
                recruiterPostedJob.getCity(),
                recruiterPostedJob.getState(),
                recruiterPostedJob.getCountry()
        );

        JobCompany jobCompany = new JobCompany(recruiterPostedJob.getCompanyId(), "", recruiterPostedJob.getName());


        return new RecruiterPostedJobDto(
                recruiterPostedJob.getTotalApplicants(),
                recruiterPostedJob.getJobPostId(),
                recruiterPostedJob.getJobTitle(),
                jobLocation,
                jobCompany
        );
    }
}
