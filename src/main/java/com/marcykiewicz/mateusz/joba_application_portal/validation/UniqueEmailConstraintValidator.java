package com.marcykiewicz.mateusz.joba_application_portal.validation;

import com.marcykiewicz.mateusz.joba_application_portal.entity.User;
import com.marcykiewicz.mateusz.joba_application_portal.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@NoArgsConstructor
public class UniqueEmailConstraintValidator implements ConstraintValidator<UniqueEmail, String> {

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    private UserService userService;

    @Autowired
    public UniqueEmailConstraintValidator(UserService userService) {
        this.userService = userService;
    }

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
