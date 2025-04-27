package com.guisebastiao.api.dtos;

import com.guisebastiao.api.enums.Category;
import com.guisebastiao.api.enums.ProductActive;
import com.guisebastiao.api.models.Product;
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
    private UserDTO saller;

    public ProductResponseDTO toDto(Product product) {
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();

        UserDTO userDTO = new UserDTO();
        userDTO.setName(product.getSaller().getName());
        userDTO.setEmail(product.getSaller().getEmail());
        userDTO.setPhone(product.getSaller().getPhone());
        userDTO.setRole(product.getSaller().getRole());

        productResponseDTO.setId(product.getId());
        productResponseDTO.setProductName(product.getProductName());
        productResponseDTO.setDescription(product.getDescription());
        productResponseDTO.setPrice(product.getPrice());
        productResponseDTO.setStock(product.getStock());
        productResponseDTO.setCategory(product.getCategory());
        productResponseDTO.setDiscount(product.getDiscount());
        productResponseDTO.setIsActive(product.getIsActive());
        productResponseDTO.setSaller(userDTO);

        return productResponseDTO;
    }
}
