package com.guisebastiao.api.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ProductImagesResponseDTO {
    private UUID productImageId;
    private String url;
}
