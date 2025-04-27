package com.guisebastiao.api.dtos;

import com.guisebastiao.api.enums.Category;
import com.guisebastiao.api.enums.ProductActive;
import com.guisebastiao.api.enums.ValidateEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductDTO {

    @NotBlank(message = "Informe o nome do produto")
    @Size(max = 250, message = "O nome do produto tem que ser menor de 250 caracteres")
    private String productName;

    @NotBlank(message = "Informe a descrição do produto")
    @Size(max = 250, message = "A descrição do produto tem que ser menor de 500 caracteres")
    private String description;

    @NotNull(message = "Informe o preço do produto")
    @Digits(integer = 10, fraction = 2, message = "O valor deve ter no máximo 8 dígitos inteiros e 2 casas decimais")
    private BigDecimal price;

    @NotNull(message = "Informe o estoque do produto")
    @Positive(message = "O estoque não pode ter um valor negativo")
    private int stock;

    @NotNull(message = "Informe uma categoria para o produto")
    @ValidateEnum(enumClass = Category.class, message = "Essa categoria não existe")
    private String category;

    @Min(value = 0, message = "O desconto do produto não pode ser de 0%")
    @Max(value = 100, message = "O desconto do produto não pode ser maior do que 100%")
    private int discount;

    @NotNull(message = "Informe se o produto está ativo ou inativo")
    @ValidateEnum(enumClass = ProductActive.class, message = "Valor está inválido")
    private String isActive;
}
