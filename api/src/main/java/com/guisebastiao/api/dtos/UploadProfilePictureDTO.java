package com.guisebastiao.api.dtos;

import com.guisebastiao.api.validations.ImageFile;
import com.guisebastiao.api.validations.MaxFileSize;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UploadProfilePictureDTO {
    @NotNull(message = "A foto de perfil não pode ficar vazia")
    @ImageFile
    @MaxFileSize(value = 5 * 1024 * 1024, message = "A foto de perfil tem que ter no máximo 5MB")
    private MultipartFile file;
}
