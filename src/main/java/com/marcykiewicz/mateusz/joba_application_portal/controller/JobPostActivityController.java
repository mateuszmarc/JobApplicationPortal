package com.marcykiewicz.mateusz.joba_application_portal.controller;

import com.marcykiewicz.mateusz.joba_application_portal.entity.JobPostActivity;
import com.marcykiewicz.mateusz.joba_application_portal.entity.User;
import com.marcykiewicz.mateusz.joba_application_portal.entity.UserProfile;
import com.marcykiewicz.mateusz.joba_application_portal.service.JobPostActivityService;
import com.marcykiewicz.mateusz.joba_application_portal.service.UserProfileService;
import com.marcykiewicz.mateusz.joba_application_portal.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/jobs")
public class JobPostActivityController {

    private final UserProfileService userProfileService;
    private final UserService userService;
    private final JobPostActivityService jobPostActivityService;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
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
            System.out.println(jobPostActivity);
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

    @GetMapping("/job-details/{id}")
    public String showJobDetails(@PathVariable Long id, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            UserProfile userProfile = userProfileService.getCurrentUserProfile();
            JobPostActivity jobPostActivity = jobPostActivityService.findById(id);

            model.addAttribute("jobPost", jobPostActivity);
            model.addAttribute("user", userProfile);
            System.out.println(userProfile);
        }

        return "job-details";
    }

    @GetMapping("/edit/{id}")
    public String showEditJobPostForm(@PathVariable Long id, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            UserProfile userProfile = userProfileService.getCurrentUserProfile();
            JobPostActivity jobPostActivity = jobPostActivityService.findById(id);
            model.addAttribute("user", userProfile);
            model.addAttribute("jobPostActivity", jobPostActivity);
        }

        return "add-jobs";
    }
}
