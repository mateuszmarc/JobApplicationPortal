package com.marcykiewicz.mateusz.joba_application_portal.service;

import com.marcykiewicz.mateusz.joba_application_portal.entity.RecruiterProfile;
import com.marcykiewicz.mateusz.joba_application_portal.repository.RecruiterProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RecruiterProfileService {

    private final RecruiterProfileRepository recruiterProfileRepository;

   public Optional<RecruiterProfile> findById(Long id) {
        return recruiterProfileRepository.findById(id);
    }

    public RecruiterProfile saveNew(RecruiterProfile recruiterProfile) {
      return recruiterProfileRepository.save(recruiterProfile);
    }
}
