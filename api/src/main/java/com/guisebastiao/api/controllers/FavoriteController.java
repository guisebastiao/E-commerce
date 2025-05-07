package com.guisebastiao.api.controllers;

import com.guisebastiao.api.dtos.DefaultResponseDTO;
import com.guisebastiao.api.services.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @PostMapping("{productId}")
    public ResponseEntity<DefaultResponseDTO> create(@PathVariable String productId) {
        DefaultResponseDTO response = this.favoriteService.create(productId);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping
    public ResponseEntity<DefaultResponseDTO> findAll(@RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "20") int limit) {
        DefaultResponseDTO response = this.favoriteService.findAll(offset, limit);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<DefaultResponseDTO> delete(@PathVariable String id) {
        DefaultResponseDTO response = this.favoriteService.delete(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
