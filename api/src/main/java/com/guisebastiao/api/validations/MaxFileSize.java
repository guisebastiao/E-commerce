package com.guisebastiao.api.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {FileSizeValidator.class, FileSizeArrayValidator.class})
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface MaxFileSize {
    String message() default "O tamanho do arquivo excede o limite máximo";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    long value();
}
