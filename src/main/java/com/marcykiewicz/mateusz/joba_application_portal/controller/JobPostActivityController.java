package com.marcykiewicz.mateusz.joba_application_portal.controller;

import com.marcykiewicz.mateusz.joba_application_portal.entity.UserProfile;
import com.marcykiewicz.mateusz.joba_application_portal.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class JobPostActivityController {

    private final UserProfileService userProfileService;

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {

        UserProfile userProfile = userProfileService.getCurrentUserProfile();
        System.out.println(userProfile);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            model.addAttribute("username", currentUsername);
        }
        model.addAttribute("user", userProfile);
        return "dashboard";
    }
}
