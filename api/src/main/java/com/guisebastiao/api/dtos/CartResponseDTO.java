package com.guisebastiao.api.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class CartResponseDTO {
    private UUID CartItemid;
    private ProductResponseDTO product;
}
