package com.guisebastiao.api.controllers;

import com.guisebastiao.api.dtos.DefaultResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HealthController {

    @GetMapping
    public ResponseEntity<DefaultResponseDTO> health() {
        DefaultResponseDTO response = new DefaultResponseDTO();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("A api está rodando");
        response.setSuccess(Boolean.TRUE);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
