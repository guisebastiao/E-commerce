package com.guisebastiao.api.dtos;

import com.guisebastiao.api.enums.Role;
import com.guisebastiao.api.exceptions.BadRequestException;
import com.guisebastiao.api.models.User;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.http.Method;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO {
    private String name;
    private String email;
    private String phone;
    private int reputation;
    private Role role;
    private String profilePictureUrl;

    public UserResponseDTO toDto(User user, MinioClient minioClient) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setReputation(user.getReputation());
        dto.setRole(user.getRole());

        if(user.getProfilePicture() != null) {
            String url = this.generatePresignedUrl(minioClient, user.getProfilePicture().getObjectId());
            dto.setProfilePictureUrl(url);
        }

        return dto;
    }

    private String generatePresignedUrl(MinioClient minioClient, String objectId) {
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket("ecommerce")
                            .object(objectId)
                            .expiry(604800)
                            .build()
            );
        } catch (Exception e) {
            throw new BadRequestException("Ocorreu um erro inesperado, tente novamente mais tarde");
        }
    }
}
