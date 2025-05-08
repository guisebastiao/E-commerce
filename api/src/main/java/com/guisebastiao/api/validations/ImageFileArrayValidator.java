package com.guisebastiao.api.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

public class ImageFileArrayValidator implements ConstraintValidator<ImageFile, MultipartFile[]> {
    private final List<String> allowedTypes = Arrays.asList(
            "image/jpeg",
            "image/png",
            "image/webp"
    );

    @Override
    public boolean isValid(MultipartFile[] files, ConstraintValidatorContext context) {
        for (MultipartFile file : files) {
            if (file != null && !file.isEmpty()) {
                String contentType = file.getContentType();

                if (contentType == null || !allowedTypes.contains(contentType.toLowerCase())) {
                    return false;
                }
            }
        }

        return true;
    }
}
