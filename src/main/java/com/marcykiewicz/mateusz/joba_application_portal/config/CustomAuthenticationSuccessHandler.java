package com.marcykiewicz.mateusz.joba_application_portal.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        System.out.printf("The username %s is logged now%n", username);

        boolean hasJobSeekerAuthority = authentication.getAuthorities().stream().anyMatch(role ->
                role.getAuthority().equals("Job Seeker"));

        boolean hasRecruiterAuthority = authentication.getAuthorities().stream().anyMatch(role ->
                role.getAuthority().equals("Recruiter"));

        if (hasJobSeekerAuthority || hasRecruiterAuthority) {
            response.sendRedirect("/dashboard/");
        }
    }
}
