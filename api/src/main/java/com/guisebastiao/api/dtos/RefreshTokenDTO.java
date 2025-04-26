package com.guisebastiao.api.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshTokenDTO {

    @NotBlank(message = "Token é obrigatório")
    private String token;
}