package com.guisebastiao.api.dtos;

import com.guisebastiao.api.enums.State;
import com.guisebastiao.api.enums.ValidateEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {
    @NotBlank(message = "Informe a rua do endereço")
    @Size(max = 255, message = "A rua tem que possuir menos de 250 caracteres")
    private String street;

    @NotBlank(message = "Informe a cidade do endereço")
    @Size(max = 255, message = "A cidade tem que possuir menos de 250 caracteres")
    private String city;

    @NotNull(message = "Informe o estado do endereço")
    @ValidateEnum(enumClass = State.class, message = "Essa categoria não existe")
    private String state;

    @NotBlank(message = "Informe o cep do endereço")
    @Size(max = 8, min = 8, message = "O cep tem que possuir 8 caracteres")
    private String zip;
}
