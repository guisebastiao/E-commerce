package com.guisebastiao.api.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CartResponseDTO {
    private UUID CartItemid;
    private int quantity;
    private ProductResponseDTO product;
}
