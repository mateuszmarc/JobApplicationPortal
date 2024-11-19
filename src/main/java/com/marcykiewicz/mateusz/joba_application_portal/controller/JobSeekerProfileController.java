package com.marcykiewicz.mateusz.joba_application_portal.controller;

import com.marcykiewicz.mateusz.joba_application_portal.entity.JobSeekerProfile;
import com.marcykiewicz.mateusz.joba_application_portal.entity.Skills;
import com.marcykiewicz.mateusz.joba_application_portal.entity.User;
import com.marcykiewicz.mateusz.joba_application_portal.service.JobSeekerProfileService;
import com.marcykiewicz.mateusz.joba_application_portal.service.UserService;
import com.marcykiewicz.mateusz.joba_application_portal.util.FileUploadUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Controller
@RequestMapping("/job-seeker-profile")
public class JobSeekerProfileController {

    private final UserService userService;
    private final JobSeekerProfileService jobSeekerProfileService;

    @GetMapping
    public String showJobSeekerProfileForm(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Skills> skills = new ArrayList<>();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            User user = userService.findUserByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Could not find the user"));

            JobSeekerProfile jobSeekerProfile = jobSeekerProfileService.findById(user.getUserId());
            if (jobSeekerProfile.getSkills().isEmpty()) {
                skills.add(new Skills());
                jobSeekerProfile.setSkills(skills);
            }
            model.addAttribute("profile", jobSeekerProfile);
        }
        return "job-seeker-profile";
    }

    @PostMapping
    public String processJobSeekerProfileForm(@Valid @ModelAttribute("profile") JobSeekerProfile profile, BindingResult bindingResult,
                                              @RequestParam("image") MultipartFile image, @RequestParam("pdf") MultipartFile resume) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {

            if (bindingResult.hasErrors()) {
                return "job-seeker-profile";
            }

            String username = authentication.getName();
            User user = userService.findUserByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Could not find the user"));
            profile.setUser(user);
            profile.setId(user.getUserId());

            profile.getSkills().forEach(skill -> skill.setJobSeekerProfile(profile));

            String imageName = "";
            String resumeName = "";

            if (!Objects.equals(image.getOriginalFilename(), "")) {
                imageName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
                profile.setProfilePhoto(imageName);
            }

            if (!Objects.equals(resume.getOriginalFilename(), "")) {
                resumeName = StringUtils.cleanPath(Objects.requireNonNull(resume.getOriginalFilename()));
                profile.setResume(resumeName);
            }

            JobSeekerProfile savedJobSeekerProfile = jobSeekerProfileService.save(profile);

            String imageUploadDir = "photos/job-seeker/" + savedJobSeekerProfile.getId();
            String resumeUploadDir = "resumes/job-seeker/" + savedJobSeekerProfile.getId();

            try {
                if (!Objects.equals(image.getOriginalFilename(), "")) {
                    FileUploadUtil.saveFile(imageUploadDir, imageName, image);
                }

                if (!Objects.equals(resume.getOriginalFilename(), "")) {
                    FileUploadUtil.saveFile(resumeUploadDir, resumeName, resume);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        return "redirect:/dashboard";

    }
}
