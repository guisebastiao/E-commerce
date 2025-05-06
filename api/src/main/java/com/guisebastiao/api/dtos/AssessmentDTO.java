package com.guisebastiao.api.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssessmentDTO {
    @NotBlank(message = "Informe o comentário sobre o produto")
    @Size(max = 300, message = "O comentário não pode ser maior do que 300 caracteres")
    private String comment;

    @Positive(message = "A nota não pode ser negativa")
    @Max(value = 5, message = "A nota não pode ser maior do que 5")
    private int note;
}
