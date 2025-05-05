package com.guisebastiao.api.controllers;

import com.guisebastiao.api.dtos.DefaultResponseDTO;
import com.guisebastiao.api.services.ProfilePictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/profile-picture")
public class ProfilePictureController {

    @Autowired
    private ProfilePictureService profilePictureService;

    @PostMapping
    public ResponseEntity<DefaultResponseDTO> uploadProfilePicture(@RequestParam MultipartFile file) throws Exception {
        DefaultResponseDTO response = this.profilePictureService.uploadProfilePicture(file);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping(value = "{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultResponseDTO> getProfilePicture(@PathVariable String userId) throws Exception {
        DefaultResponseDTO response = this.profilePictureService.getProfilePicture(userId);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
