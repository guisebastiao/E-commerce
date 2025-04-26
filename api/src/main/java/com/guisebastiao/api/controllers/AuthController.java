package com.guisebastiao.api.controllers;

import com.guisebastiao.api.dtos.DefaultResponseDTO;
import com.guisebastiao.api.dtos.LoginDTO;
import com.guisebastiao.api.dtos.RefreshTokenDTO;
import com.guisebastiao.api.dtos.RegisterDTO;
import com.guisebastiao.api.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<DefaultResponseDTO> login(@RequestBody @Valid LoginDTO dto) {
        DefaultResponseDTO response = this.authService.login(dto);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping("/register")
    public ResponseEntity<DefaultResponseDTO> register(@RequestBody @Valid RegisterDTO dto) {
        DefaultResponseDTO response = this.authService.register(dto);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<DefaultResponseDTO> refreshToken(@RequestBody @Valid RefreshTokenDTO dto) {
        DefaultResponseDTO response = this.authService.refreshToken(dto);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
