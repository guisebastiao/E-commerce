package com.guisebastiao.api.controllers;

import com.guisebastiao.api.dtos.AssessmentDTO;
import com.guisebastiao.api.dtos.DefaultResponseDTO;
import com.guisebastiao.api.services.AssessmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/assessments")
public class AssessmentController {

    @Autowired
    private AssessmentService assessmentService;

    @PostMapping("{productId}")
    public ResponseEntity<DefaultResponseDTO> create(@PathVariable String productId, @RequestBody @Valid AssessmentDTO dto) {
        DefaultResponseDTO response = this.assessmentService.create(productId, dto);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("{productId}")
    public ResponseEntity<DefaultResponseDTO> findAll(@PathVariable String productId, @RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "20") int limit) {
        DefaultResponseDTO response = this.assessmentService.findAll(productId, offset, limit);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<DefaultResponseDTO> update(@PathVariable String id, @RequestBody @Valid AssessmentDTO dto) {
        DefaultResponseDTO response = this.assessmentService.update(id, dto);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<DefaultResponseDTO> delete(@PathVariable String id) {
        DefaultResponseDTO response = this.assessmentService.delete(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
