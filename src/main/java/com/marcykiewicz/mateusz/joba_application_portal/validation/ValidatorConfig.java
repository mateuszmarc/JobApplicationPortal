package com.marcykiewicz.mateusz.joba_application_portal.validation;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class ValidatorConfig {

    private final SpringConstraintValidatorFactory constraintValidatorFactory;

    public Validator validator() {
        try (ValidatorFactory factory = Validation.byDefaultProvider()
                .configure()
                .constraintValidatorFactory(constraintValidatorFactory)
                .buildValidatorFactory()) {
            return factory.getValidator();
        }
    }
}
