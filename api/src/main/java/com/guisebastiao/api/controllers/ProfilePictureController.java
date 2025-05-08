package com.guisebastiao.api.controllers;

import com.guisebastiao.api.dtos.DefaultResponseDTO;
import com.guisebastiao.api.dtos.UploadProfilePictureDTO;
import com.guisebastiao.api.services.ProfilePictureService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile-picture")
public class ProfilePictureController {

    @Autowired
    private ProfilePictureService profilePictureService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DefaultResponseDTO> uploadProfilePicture(@ModelAttribute @Valid UploadProfilePictureDTO dto) throws Exception {
        DefaultResponseDTO response = this.profilePictureService.uploadProfilePicture(dto.getFile());
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping(value = "{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultResponseDTO> getProfilePicture(@PathVariable String userId) throws Exception {
        DefaultResponseDTO response = this.profilePictureService.getProfilePicture(userId);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping
    public ResponseEntity<DefaultResponseDTO> deleteProfilePicture() throws Exception {
        DefaultResponseDTO response = this.profilePictureService.deleteProfilePicture();
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
