package com.guisebastiao.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddCartItemDTO {

    @NotBlank(message = "Informe o id do produto")
    private String productId;

    @NotNull(message = "Informe a quantidade do produto")
    private int quantity;
}
