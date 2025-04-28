package com.guisebastiao.api.controllers;

import com.guisebastiao.api.dtos.AddCartItemDTO;
import com.guisebastiao.api.dtos.DefaultResponseDTO;
import com.guisebastiao.api.services.CartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping
    public ResponseEntity<DefaultResponseDTO> create(@RequestBody @Valid AddCartItemDTO dto) {
        DefaultResponseDTO response = this.cartService.create(dto);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping
    public ResponseEntity<DefaultResponseDTO> getAllCartItems(@RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "20") int limit) {
        DefaultResponseDTO response = this.cartService.getAllCartItems(offset, limit);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
