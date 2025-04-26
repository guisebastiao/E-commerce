package com.guisebastiao.api.dtos;

import com.guisebastiao.api.enums.Role;
import com.guisebastiao.api.enums.ValidateEnum;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDTO {

    @NotBlank(message = "Informe seu e-mail")
    @Size(max = 100, message = "O nome tem que ser menor de 100 caracteres")
    private String name;

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

    @NotBlank(message = "Informe seu e-mail")
    @Size(max = 13, message = "O seu número de telefone tem que ser menor de 13 caracteres")
    private String phone;

    @NotNull(message = "Defina a regra do usuário")
    @ValidateEnum(enumClass = Role.class, message = "Essa regra para usuario não é valida")
    private String role;
}
