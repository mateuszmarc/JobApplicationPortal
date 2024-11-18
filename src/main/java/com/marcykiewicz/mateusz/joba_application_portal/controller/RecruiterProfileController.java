package com.marcykiewicz.mateusz.joba_application_portal.controller;

import com.marcykiewicz.mateusz.joba_application_portal.entity.RecruiterProfile;
import com.marcykiewicz.mateusz.joba_application_portal.entity.User;
import com.marcykiewicz.mateusz.joba_application_portal.exception.DatabaseIntegrityException;
import com.marcykiewicz.mateusz.joba_application_portal.service.RecruiterProfileService;
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

import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping("/recruiter-profile")
public class RecruiterProfileController {

    private final RecruiterProfileService recruiterProfileService;
    private final UserService userService;

    @GetMapping
    public String showRecruiterForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();

            User user = userService.findUserByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Could not find user"));

            Optional<RecruiterProfile> recruiterProfile = recruiterProfileService.findById(user.getUserId());

            recruiterProfile.map(profile -> model.addAttribute("profile", profile))
                    .orElseThrow(() -> new DatabaseIntegrityException("There is no RecruiterProfile in database for given user"));

        } else {
            return "redirect:/login";
        }
        return "recruiter-profile";
    }

    @PostMapping
    public String processRecruiterForm(@Valid @ModelAttribute RecruiterProfile recruiterProfile, BindingResult bindingResult,
                                       @RequestParam("image") MultipartFile multipartFile, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {

            if (bindingResult.hasErrors()) {
                return "recruiter-profile";
            }

            String username = authentication.getName();
            User user = userService.findUserByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Could not find user"));
            recruiterProfile.setUser(user);
            recruiterProfile.setId(user.getUserId());

            String fileName = "";

            if (!Objects.equals(multipartFile.getOriginalFilename(), "")) {
                fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
                recruiterProfile.setProfilePhoto(fileName);
            }

            RecruiterProfile savedProfile = recruiterProfileService.saveNew(recruiterProfile);

            String uploadDir = "photos/recruiter/" + savedProfile.getId();

            try {
                FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "redirect:/dashboard";
    }
}
