package com.marcykiewicz.mateusz.joba_application_portal.controller;

import com.marcykiewicz.mateusz.joba_application_portal.entity.JobSeekerProfile;
import com.marcykiewicz.mateusz.joba_application_portal.entity.User;
import com.marcykiewicz.mateusz.joba_application_portal.service.JobSeekerProfileService;
import com.marcykiewicz.mateusz.joba_application_portal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/job-seeker-profile")
public class JobSeekerProfileController {

    private final UserService userService;
    private final JobSeekerProfileService jobSeekerProfileService;

    @GetMapping
    public String showJobSeekerProfileForm(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            User user = userService.findUserByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Could not find the user"));

            JobSeekerProfile jobSeekerProfile = jobSeekerProfileService.findById(user.getUserId());
            System.out.println(jobSeekerProfile);
            model.addAttribute("profile", jobSeekerProfile);
        }
        return "job-seeker-profile";
    }
}
