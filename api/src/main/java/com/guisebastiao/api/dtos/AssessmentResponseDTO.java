package com.guisebastiao.api.dtos;

import com.guisebastiao.api.models.Assessment;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AssessmentResponseDTO {
    private UUID id;
    private UserDTO user;
    private String comment;
    private int note;

    public AssessmentResponseDTO toDto(Assessment assessment) {
        UserDTO userDTO = new UserDTO();
        AssessmentResponseDTO dto = new AssessmentResponseDTO();
        dto.setId(assessment.getId());
        dto.setUser(userDTO.toDto(assessment.getUser()));
        dto.setComment(assessment.getComment());
        dto.setNote(assessment.getNote());
        return dto;
    }
}
