package com.marcykiewicz.mateusz.joba_application_portal.controller;

import com.marcykiewicz.mateusz.joba_application_portal.entity.RecruiterProfile;
import com.marcykiewicz.mateusz.joba_application_portal.entity.UserProfile;
import com.marcykiewicz.mateusz.joba_application_portal.entity.dto.RecruiterPostedJobDto;
import com.marcykiewicz.mateusz.joba_application_portal.service.JobPostActivityService;
import com.marcykiewicz.mateusz.joba_application_portal.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final UserProfileService userProfileService;
    private final JobPostActivityService jobPostActivityService;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping
    public String showDashboard(Model model) {

        UserProfile userProfile = userProfileService.getCurrentUserProfile();
        System.out.println(userProfile);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            model.addAttribute("username", currentUsername);
            model.addAttribute("user", userProfile);

            if (userProfile instanceof RecruiterProfile) {
                List<RecruiterPostedJobDto> postedJobs = jobPostActivityService.getRecruiterPostedJobs(((RecruiterProfile) userProfile).getId());
                model.addAttribute("postedJobs", postedJobs);
            }

            return "dashboard";
        }
        model.addAttribute("user", userProfile);
        return "dashboard";
    }
}
