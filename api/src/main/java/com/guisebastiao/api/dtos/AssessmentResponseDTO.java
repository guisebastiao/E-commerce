package com.guisebastiao.api.dtos;

import com.guisebastiao.api.models.Assessment;
import io.minio.MinioClient;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AssessmentResponseDTO {
    private UUID id;
    private UserResponseDTO user;
    private String comment;
    private int note;

    public AssessmentResponseDTO toDto(Assessment assessment, MinioClient minioClient) {
        UserResponseDTO userDTO = new UserResponseDTO();
        AssessmentResponseDTO dto = new AssessmentResponseDTO();
        dto.setId(assessment.getId());
        dto.setUser(userDTO.toDto(assessment.getUser(), minioClient));
        dto.setComment(assessment.getComment());
        dto.setNote(assessment.getNote());
        return dto;
    }
}
