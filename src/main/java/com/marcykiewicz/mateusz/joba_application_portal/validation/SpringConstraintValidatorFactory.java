package com.marcykiewicz.mateusz.joba_application_portal.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SpringConstraintValidatorFactory implements ConstraintValidatorFactory {

    private final AutowireCapableBeanFactory beanFactory;

    @Override
    public <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> aClass) {
        return beanFactory.createBean(aClass);
    }

    @Override
    public void releaseInstance(ConstraintValidator<?, ?> constraintValidator) {
    }
}
