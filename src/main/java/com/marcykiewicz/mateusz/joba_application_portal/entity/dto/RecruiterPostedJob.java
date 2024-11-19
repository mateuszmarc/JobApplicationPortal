package com.marcykiewicz.mateusz.joba_application_portal.entity.dto;

public interface RecruiterPostedJob {

    Long getTotalApplicants();

    Long getJobPostId();

    String getJobTitle();

    Long getLocationId();

    String getCity();

    String getState();

    String getCountry();

    Long getCompanyId();

    String getName();

}
