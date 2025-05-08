package com.guisebastiao.api.controllers;

import com.guisebastiao.api.dtos.DefaultResponseDTO;
import com.guisebastiao.api.dtos.UploadProductImagesDTO;
import com.guisebastiao.api.services.ProductImageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product-images")
public class ProductImageController {

    @Autowired
    private ProductImageService productImageService;

    @PostMapping("{productId}")
    public ResponseEntity<DefaultResponseDTO> create(@PathVariable String productId, @ModelAttribute @Valid UploadProductImagesDTO dto) {
        DefaultResponseDTO response = this.productImageService.createProductImage(productId, dto.getFiles());
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("{productId}")
    public ResponseEntity<DefaultResponseDTO> findProductImages(@PathVariable String productId) {
        DefaultResponseDTO response = this.productImageService.findProductImages(productId);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping("{productImageId}")
    public ResponseEntity<DefaultResponseDTO> deleteProductImage(@PathVariable String productImageId) {
        DefaultResponseDTO response = this.productImageService.deleteProductImage(productImageId);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
