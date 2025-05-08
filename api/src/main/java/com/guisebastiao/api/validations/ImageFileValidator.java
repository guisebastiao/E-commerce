package com.guisebastiao.api.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

public class ImageFileValidator implements ConstraintValidator<ImageFile, MultipartFile> {

    private final List<String> allowedTypes = Arrays.asList(
            "image/jpeg",
            "image/png",
            "image/webp"
    );

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        return file == null || allowedTypes.contains(file.getContentType());
    }
}