package com.guisebastiao.api.dtos;

import com.guisebastiao.api.enums.Category;
import com.guisebastiao.api.enums.ProductActive;
import com.guisebastiao.api.models.Product;
import io.minio.MinioClient;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class ProductResponseDTO {
    private UUID id;
    private String productName;
    private String description;
    private BigDecimal price;
    private int stock;
    private Category category;
    private int discount;
    private ProductActive isActive;
    private UserResponseDTO saller;

    public ProductResponseDTO toDto(Product product, MinioClient minioClient) {
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();

        UserResponseDTO userDTO = new UserResponseDTO();

        productResponseDTO.setId(product.getId());
        productResponseDTO.setProductName(product.getProductName());
        productResponseDTO.setDescription(product.getDescription());
        productResponseDTO.setPrice(product.getPrice());
        productResponseDTO.setStock(product.getStock());
        productResponseDTO.setCategory(product.getCategory());
        productResponseDTO.setDiscount(product.getDiscount());
        productResponseDTO.setIsActive(product.getIsActive());
        productResponseDTO.setSaller(userDTO.toDto(product.getSaller(), minioClient));

        return productResponseDTO;
    }
}
