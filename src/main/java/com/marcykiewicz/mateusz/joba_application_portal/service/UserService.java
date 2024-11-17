package com.marcykiewicz.mateusz.joba_application_portal.service;

import com.marcykiewicz.mateusz.joba_application_portal.entity.JobSeekerProfile;
import com.marcykiewicz.mateusz.joba_application_portal.entity.RecruiterProfile;
import com.marcykiewicz.mateusz.joba_application_portal.entity.User;
import com.marcykiewicz.mateusz.joba_application_portal.repository.JobSeekerProfileRepository;
import com.marcykiewicz.mateusz.joba_application_portal.repository.RecruiterProfileRepository;
import com.marcykiewicz.mateusz.joba_application_portal.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;

    @Transactional
    public void addUser(User savedUser) {
        savedUser.setActive(true);

        savedUser = userRepository.save(savedUser);
        Long userTypeId = savedUser.getUsersType().getUserTypeId();

        if (userTypeId.equals(1L)) {
            recruiterProfileRepository.save(new RecruiterProfile(savedUser));
        } else if (userTypeId.equals(2L)) {
            jobSeekerProfileRepository.save(new JobSeekerProfile(savedUser));
        }
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public String validatePasswords(String password, String passwordRepeat) {
        String errorMessage = null;
        if (!(passwordRepeat != null && passwordRepeat.equals(password))) {
            errorMessage = "Given passwords are different";
        }
        return errorMessage;
    }
}
