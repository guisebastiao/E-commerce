package com.guisebastiao.api.services;

import com.guisebastiao.api.dtos.DefaultResponseDTO;
import com.guisebastiao.api.dtos.ProfilePictureDTO;
import com.guisebastiao.api.exceptions.EntityNotFoundException;
import com.guisebastiao.api.models.ProfilePicture;
import com.guisebastiao.api.models.User;
import com.guisebastiao.api.repositories.ProfilePictureRepository;
import com.guisebastiao.api.repositories.UserRepository;
import com.guisebastiao.api.utils.UUIDConverter;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProfilePictureService {

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private ProfilePictureRepository profilePictureRepository;

    @Autowired
    private UserRepository userRepository;

    public DefaultResponseDTO uploadProfilePicture(MultipartFile file) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Optional<ProfilePicture> existProfilePicture = profilePictureRepository.findByUser(user);

        if(existProfilePicture.isPresent()) {
            this.profilePictureRepository.delete(existProfilePicture.get());
        }

        InputStream inputStream = file.getInputStream();
        String objectId = UUID.randomUUID().toString();

        this.minioClient.putObject(PutObjectArgs.builder().bucket("ecommerce").object(objectId).stream(inputStream, inputStream.available(), -1).contentType("image/jpeg").build());

        ProfilePicture profilePicture = new ProfilePicture();
        profilePicture.setObjectId(objectId);
        profilePicture.setUser(user);

        profilePictureRepository.save(profilePicture);

        DefaultResponseDTO response = new DefaultResponseDTO();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Imagem de perfil adicionado com sucesso");
        response.setSuccess(Boolean.TRUE);

        return response;
    }

    public DefaultResponseDTO getProfilePicture(String userId) throws Exception {
        User user = this.userRepository.findById(UUIDConverter.toUUID(userId))
                .orElseThrow(() -> new EntityNotFoundException("Imagem de perfil não disponivel"));

        var objectId = user.getProfilePicture().getObjectId();

        String url = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().method(Method.GET).bucket("ecommerce").object(objectId).expiry(604800).build());

        ProfilePictureDTO profilePictureDTO = new ProfilePictureDTO();
        profilePictureDTO.setProfilePictureUrl(url);

        DefaultResponseDTO response = new DefaultResponseDTO();
        response.setStatus(HttpStatus.OK.value());
        response.setData(profilePictureDTO);
        response.setMessage("Imagem de perfil encontrado com sucesso");
        response.setSuccess(Boolean.TRUE);

        return response;
    }
}
