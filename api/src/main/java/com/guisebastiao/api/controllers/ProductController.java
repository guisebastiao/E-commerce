package com.guisebastiao.api.controllers;

import com.guisebastiao.api.dtos.DefaultResponseDTO;
import com.guisebastiao.api.dtos.ProductDTO;
import com.guisebastiao.api.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<DefaultResponseDTO> create(@RequestBody @Valid ProductDTO dto) {
        DefaultResponseDTO responseDTO = this.productService.create(dto);
        return ResponseEntity.status(responseDTO.getStatus()).body(responseDTO);
    }

    @GetMapping("{id}")
    public ResponseEntity<DefaultResponseDTO> findById(@PathVariable String id) {
        DefaultResponseDTO responseDTO = this.productService.findById(id);
        return ResponseEntity.status(responseDTO.getStatus()).body(responseDTO);
    }

    @PutMapping("{id}")
    public ResponseEntity<DefaultResponseDTO> update(@PathVariable String id, @RequestBody @Valid ProductDTO dto) {
        DefaultResponseDTO responseDTO = this.productService.update(id, dto);
        return ResponseEntity.status(responseDTO.getStatus()).body(responseDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<DefaultResponseDTO> update(@PathVariable String id) {
        DefaultResponseDTO responseDTO = this.productService.delete(id);
        return ResponseEntity.status(responseDTO.getStatus()).body(responseDTO);
    }
}
