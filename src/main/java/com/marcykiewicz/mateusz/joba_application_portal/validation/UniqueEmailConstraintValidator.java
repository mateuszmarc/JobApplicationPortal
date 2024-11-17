package com.marcykiewicz.mateusz.joba_application_portal.validation;

import com.marcykiewicz.mateusz.joba_application_portal.entity.User;
import com.marcykiewicz.mateusz.joba_application_portal.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UniqueEmailConstraintValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    private UserService userService;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if (email != null) {
            return !checkForDuplicates(email);
        }
        return false;
    }

    private boolean checkForDuplicates(String email) {
        Optional<User> optionalUser = userService.findUserByEmail(email);
        return optionalUser.isPresent();
    }
}
