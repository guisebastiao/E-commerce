package com.guisebastiao.api.controllers;

import com.guisebastiao.api.dtos.AddressDTO;
import com.guisebastiao.api.dtos.DefaultResponseDTO;
import com.guisebastiao.api.services.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping
    public ResponseEntity<DefaultResponseDTO> create(@RequestBody @Valid AddressDTO dto) {
        DefaultResponseDTO response = this.addressService.create(dto);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping
    public ResponseEntity<DefaultResponseDTO> findAllAddressByUser(@RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "20") int limit) {
        DefaultResponseDTO response = this.addressService.findAllAddressByUser(offset, limit);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<DefaultResponseDTO> update(@PathVariable String id, @RequestBody @Valid AddressDTO dto) {
        DefaultResponseDTO response = this.addressService.update(id, dto);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<DefaultResponseDTO> delete(@PathVariable String id) {
        DefaultResponseDTO response = this.addressService.delete(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
