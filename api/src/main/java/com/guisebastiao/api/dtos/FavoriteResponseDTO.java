package com.guisebastiao.api.dtos;

import com.guisebastiao.api.models.Favorite;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class FavoriteResponseDTO {
    private UUID id;
    private ProductResponseDTO product;

    public FavoriteResponseDTO toDto(Favorite favorite) {
        FavoriteResponseDTO dto = new FavoriteResponseDTO();
        ProductResponseDTO productDto = new ProductResponseDTO();
        dto.setId(favorite.getId());
        dto.setProduct(productDto.toDto(favorite.getProduct()));
        return dto;
    }
}
