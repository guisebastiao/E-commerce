package com.guisebastiao.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDTO {

    @NotBlank(message = "Informe seu e-mail")
    @Size(max = 100, message = "O nome tem que ser menor de 100 caracteres")
    private String name;

    @NotBlank(message = "Informe seu número de telefone")
    @Size(max = 13, message = "O seu número de telefone tem que ser menor de 13 caracteres")
    private String phone;
}
