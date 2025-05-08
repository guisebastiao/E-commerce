package com.guisebastiao.api.services;

import com.guisebastiao.api.dtos.DefaultResponseDTO;
import com.guisebastiao.api.dtos.UserResponseDTO;
import com.guisebastiao.api.dtos.UserUpdateDTO;
import com.guisebastiao.api.exceptions.EntityNotFoundException;
import com.guisebastiao.api.models.User;
import com.guisebastiao.api.repositories.UserRepository;
import com.guisebastiao.api.utils.UUIDConverter;
import io.minio.MinioClient;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MinioClient minioClient;

    public DefaultResponseDTO getUserById(String id) {
        User user = this.userRepository.findById(UUIDConverter.toUUID(id))
                .orElseThrow(() -> new EntityNotFoundException("Essa conta não foi encontrado"));

        UserResponseDTO userDTO = new UserResponseDTO();

        DefaultResponseDTO response = new DefaultResponseDTO();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Conta encontrada com sucesso");
        response.setData(userDTO.toDto(user, minioClient));
        response.setSuccess(Boolean.TRUE);

        return response;
    }

    public DefaultResponseDTO update(String id, UserUpdateDTO dto) {
        User user = this.userRepository.findById(UUIDConverter.toUUID(id))
                .orElseThrow(() -> new EntityNotFoundException("Essa conta não foi encontrado"));

        User newUser = new User();
        newUser.setId(user.getId());
        newUser.setEmail(user.getEmail());
        newUser.setPhone(dto.getPhone());
        newUser.setName(dto.getName());
        newUser.setPassword(user.getPassword());
        newUser.setRole(user.getRole());

        this.userRepository.save(newUser);

        DefaultResponseDTO response = new DefaultResponseDTO();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Sua conta foi atualizada com sucesso");
        response.setSuccess(Boolean.TRUE);

        return response;
    }

    public DefaultResponseDTO delete(String id) {
        User user = this.userRepository.findById(UUIDConverter.toUUID(id))
                .orElseThrow(() -> new EntityNotFoundException("Essa conta não foi encontrado"));

        this.userRepository.delete(user);

        DefaultResponseDTO response = new DefaultResponseDTO();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Sua conta foi deletada com sucesso");
        response.setSuccess(Boolean.TRUE);

        return response;
    }

}
