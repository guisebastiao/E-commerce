package com.guisebastiao.api.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {ImageFileValidator.class, ImageFileArrayValidator.class})
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ImageFile {
    String message() default "O arquivo deve ser uma imagem JPEG, JPG ou PNG";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
