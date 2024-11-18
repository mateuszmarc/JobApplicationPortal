package com.marcykiewicz.mateusz.joba_application_portal.service;

import com.marcykiewicz.mateusz.joba_application_portal.entity.JobSeekerProfile;
import com.marcykiewicz.mateusz.joba_application_portal.entity.RecruiterProfile;
import com.marcykiewicz.mateusz.joba_application_portal.entity.User;
import com.marcykiewicz.mateusz.joba_application_portal.entity.UserProfile;
import com.marcykiewicz.mateusz.joba_application_portal.repository.JobSeekerProfileRepository;
import com.marcykiewicz.mateusz.joba_application_portal.repository.RecruiterProfileRepository;
import com.marcykiewicz.mateusz.joba_application_portal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserProfileService {

    private final RecruiterProfileRepository recruiterProfileRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final UserRepository userRepository;

    public UserProfile getCurrentUserProfile() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserProfile userProfile = null;

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();

            User user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Could not find user with given username: %s".formatted(username)));

            Long userId = user.getUserId();

            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))) {
                userProfile = recruiterProfileRepository.findById(userId).orElse(new RecruiterProfile());
            } else {
                userProfile = jobSeekerProfileRepository.findById(userId).orElse(new JobSeekerProfile());
            }
        }
        return userProfile;

    }
}
