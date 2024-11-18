package com.marcykiewicz.mateusz.joba_application_portal.controller;

import com.marcykiewicz.mateusz.joba_application_portal.entity.JobPostActivity;
import com.marcykiewicz.mateusz.joba_application_portal.entity.User;
import com.marcykiewicz.mateusz.joba_application_portal.entity.UserProfile;
import com.marcykiewicz.mateusz.joba_application_portal.service.JobPostActivityService;
import com.marcykiewicz.mateusz.joba_application_portal.service.UserProfileService;
import com.marcykiewicz.mateusz.joba_application_portal.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/dashboard")
public class JobPostActivityController {

    private final UserProfileService userProfileService;
    private final UserService userService;
    private final JobPostActivityService jobPostActivityService;

    @GetMapping
    public String showDashboard(Model model) {

        UserProfile userProfile = userProfileService.getCurrentUserProfile();
        System.out.println(userProfile);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            model.addAttribute("username", currentUsername);
            model.addAttribute("user", userProfile);
            return "dashboard";
        }
        model.addAttribute("user", userProfile);
        return "dashboard";
    }

    @GetMapping("/new-job")
    public String showAddJobForm(Model model) {
        UserProfile userProfile = userProfileService.getCurrentUserProfile();
        model.addAttribute("user", userProfile);
        model.addAttribute("jobPostActivity", new JobPostActivity());

        return "add-jobs";
    }

    @PostMapping("/new-job")
    public String processAddJobForm(@Valid @ModelAttribute JobPostActivity jobPostActivity, BindingResult bindingResult, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String email = authentication.getName();
            if (bindingResult.hasErrors()) {
                UserProfile userProfile = userProfileService.getCurrentUserProfile();
                model.addAttribute("user", userProfile);
                return "add-jobs";
            }

            User user = userService.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            jobPostActivity.setUser(user);

            jobPostActivityService.save(jobPostActivity);
        }

        return "redirect:/dashboard";
    }
}
