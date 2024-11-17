package com.marcykiewicz.mateusz.joba_application_portal.controller;

import com.marcykiewicz.mateusz.joba_application_portal.entity.User;
import com.marcykiewicz.mateusz.joba_application_portal.entity.UsersType;
import com.marcykiewicz.mateusz.joba_application_portal.service.UserService;
import com.marcykiewicz.mateusz.joba_application_portal.service.UsersTypeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class RegistrationController {

    private final UsersTypeService usersTypeService;
    private final UserService userService;

    @ModelAttribute(name = "usersTypes")
    public List<UsersType> setUsersTypes() {
        return usersTypeService.findAll();
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());

        return "register";
    }

    @PostMapping("/register")
    public String processRegisterForm(@Valid @ModelAttribute User user, BindingResult bindingResult, Model model, HttpServletRequest request) {
        String passwordRepeat = request.getParameter("passwordRepeat");
        String passwordEqualityError = userService.validatePasswords(user.getPassword(), passwordRepeat);

        if (bindingResult.hasErrors() || passwordEqualityError != null) {
            model.addAttribute("passwordError", passwordEqualityError);
            return "register";
        }

        userService.addUser(user);
        return "dashboard";
    }
}
