package com.marcykiewicz.mateusz.joba_application_portal.repository;

import com.marcykiewicz.mateusz.joba_application_portal.entity.JobSeekerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobSeekerProfileRepository extends JpaRepository<JobSeekerProfile, Long> {
}
