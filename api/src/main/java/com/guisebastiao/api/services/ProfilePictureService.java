package com.guisebastiao.api.services;

import com.guisebastiao.api.dtos.DefaultResponseDTO;
import com.guisebastiao.api.dtos.ProfilePictureDTO;
import com.guisebastiao.api.exceptions.BadRequestException;
import com.guisebastiao.api.exceptions.EntityNotFoundException;
import com.guisebastiao.api.models.ProfilePicture;
import com.guisebastiao.api.models.User;
import com.guisebastiao.api.repositories.ProfilePictureRepository;
import com.guisebastiao.api.repositories.UserRepository;
import com.guisebastiao.api.utils.UUIDConverter;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;
import java.util.zip.DataFormatException;

@Service
public class ProfilePictureService {

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private ProfilePictureRepository profilePictureRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public DefaultResponseDTO uploadProfilePicture(MultipartFile file) throws Exception {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<ProfilePicture> existingPicture = this.profilePictureRepository.findByUser(user);

        if (existingPicture.isPresent()) {
            ProfilePicture profilePicture = existingPicture.get();

            this.minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket("ecommerce")
                    .object(profilePicture.getObjectId())
                    .build());

            this.profilePictureRepository.deleteByUser(user);
        }

        InputStream inputStream = file.getInputStream();
        String objectId = UUID.randomUUID().toString();

        this.minioClient.putObject(PutObjectArgs.builder().bucket("ecommerce").object(objectId).stream(inputStream, inputStream.available(), -1).contentType("image/jpeg").build());

        ProfilePicture profilePicture = new ProfilePicture();
        profilePicture.setObjectId(objectId);
        profilePicture.setUser(user);

        this.profilePictureRepository.save(profilePicture);

        DefaultResponseDTO response = new DefaultResponseDTO();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Imagem de perfil adicionado com sucesso");
        response.setSuccess(Boolean.TRUE);

        return response;
    }

    public DefaultResponseDTO getProfilePicture(String userId) throws Exception {
        User user = this.userRepository.findById(UUIDConverter.toUUID(userId))
                .orElseThrow(() -> new EntityNotFoundException("Imagem de perfil não disponivel"));

        if(user.getProfilePicture() == null) {
            throw new EntityNotFoundException("A foto de perfil não foi encontrada");
        }

        var objectId = user.getProfilePicture().getObjectId();

        String url = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .method(Method.GET)
                .bucket("ecommerce")
                .object(objectId)
                .expiry(604800)
                .build());

        ProfilePictureDTO profilePictureDTO = new ProfilePictureDTO();
        profilePictureDTO.setProfilePictureUrl(url);

        DefaultResponseDTO response = new DefaultResponseDTO();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Imagem de perfil encontrado com sucesso");
        response.setData(profilePictureDTO);
        response.setSuccess(Boolean.TRUE);

        return response;
    }

    @Transactional
    public DefaultResponseDTO deleteProfilePicture() throws Exception {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        ProfilePicture profilePicture = this.profilePictureRepository.findByUser(user)
                .orElseThrow(() -> new EntityNotFoundException("Foto de perfil não foi encontrada"));

        this.minioClient.removeObject(RemoveObjectArgs.builder()
                .bucket("ecommerce")
                .object(profilePicture.getObjectId())
                .build());


        this.profilePictureRepository.deleteByUser(user);

        DefaultResponseDTO response = new DefaultResponseDTO();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Sua imagem de perfil foi deletada com sucesso");
        response.setSuccess(Boolean.TRUE);
        return response;
    }
}
