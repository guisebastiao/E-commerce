package com.guisebastiao.api.dtos;

import com.guisebastiao.api.validations.ImageFile;
import com.guisebastiao.api.validations.MaxFileSize;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UploadProductImagesDTO {
    @NotNull(message = "A foto do produto não pode ficar vazia")
    @ImageFile
    @MaxFileSize(value = 5 * 1024 * 1024, message = "A foto do produto não pode ter mais de 5MB")
    private MultipartFile[] files;
}
