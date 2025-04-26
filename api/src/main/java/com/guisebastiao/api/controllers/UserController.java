package com.guisebastiao.api.controllers;

import com.guisebastiao.api.dtos.DefaultResponseDTO;
import com.guisebastiao.api.dtos.UserUpdateDTO;
import com.guisebastiao.api.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("{id}")
    public ResponseEntity<DefaultResponseDTO> findUserById(@PathVariable String id) {
        DefaultResponseDTO response = this.userService.getUserById(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<DefaultResponseDTO> update(@PathVariable String id, @RequestBody @Valid UserUpdateDTO dto) {
        DefaultResponseDTO response = this.userService.update(id, dto);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<DefaultResponseDTO> delete(@PathVariable String id) {
        DefaultResponseDTO response = this.userService.delete(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
