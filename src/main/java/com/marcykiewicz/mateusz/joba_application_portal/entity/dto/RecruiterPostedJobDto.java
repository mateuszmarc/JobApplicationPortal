package com.marcykiewicz.mateusz.joba_application_portal.entity.dto;

import com.marcykiewicz.mateusz.joba_application_portal.entity.JobCompany;
import com.marcykiewicz.mateusz.joba_application_portal.entity.JobLocation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class RecruiterPostedJobDto {

    private Long totalApplicants;
    private Long jobPostId;
    private String jobTitle;
    private JobLocation jobLocation;
    private JobCompany jobCompany;


}
