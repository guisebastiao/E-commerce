package com.guisebastiao.api.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {

    @NotBlank(message = "Informe seu e-mail")
    @Size(max = 250, message = "O e-mail tem que ser menor de 250 caracteres")
    @Email(message = "E-mail inválido")
    private String email;

    @NotBlank(message = "Informe sua senha")
    @Size(min = 6, message = "A senha tem que ter no minímo de 6 caracteres")
    @Size(max = 20, message = "A senha tem que ter no máximo de 20 caracteres")
    @Pattern.List({
            @Pattern(regexp = ".*[A-Z].*", message = "A senha deve conter pelo menos uma letra maiúscula"),
            @Pattern(regexp = ".*\\d.*\\d.*", message = "A senha deve conter pelo menos dois números"),
            @Pattern(regexp = ".*[@$!%*?&.#].*", message = "A senha deve conter pelo menos um caractere especial")
    })
    private String password;
}
