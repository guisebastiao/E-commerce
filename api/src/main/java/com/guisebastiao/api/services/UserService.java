package com.guisebastiao.api.services;

import com.guisebastiao.api.dtos.DefaultResponseDTO;
import com.guisebastiao.api.dtos.UserDTO;
import com.guisebastiao.api.dtos.UserUpdateDTO;
import com.guisebastiao.api.exceptions.BadRequestException;
import com.guisebastiao.api.exceptions.EntityNotFoundException;
import com.guisebastiao.api.models.User;
import com.guisebastiao.api.repositories.UserRepository;
import com.guisebastiao.api.utils.UUIDConverter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@NoArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public DefaultResponseDTO getUserById(String id) {
        UUID userId = UUIDConverter.toUUID(id);
        Optional<User> user = this.userRepository.findById(userId);

        if(user.isEmpty()) {
            throw new EntityNotFoundException("Essa conta não foi encontrado");
        }

        UserDTO userDTO = new UserDTO();

        DefaultResponseDTO response = new DefaultResponseDTO();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Conta encontrada com sucesso");
        response.setData(userDTO.toDto(user.get()));
        response.setSuccess(Boolean.TRUE);

        return response;
    }

    public DefaultResponseDTO update(String id, UserUpdateDTO dto) {
        UUID userId = UUIDConverter.toUUID(id);
        Optional<User> userExist = this.userRepository.findById(userId);

        if(userExist.isEmpty()) {
            throw new EntityNotFoundException("Essa conta não foi encontrado");
        }

        User user = new User();
        user.setId(userExist.get().getId());
        user.setEmail(userExist.get().getEmail());
        user.setPhone(dto.getPhone());
        user.setName(dto.getName());
        user.setPassword(userExist.get().getPassword());
        user.setRole(userExist.get().getRole());

        this.userRepository.save(user);

        DefaultResponseDTO response = new DefaultResponseDTO();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Sua conta foi atualizada com sucesso");
        response.setSuccess(Boolean.TRUE);

        return response;
    }

    public DefaultResponseDTO delete(String id) {
        UUID userId = UUIDConverter.toUUID(id);
        Optional<User> user = this.userRepository.findById(userId);

        if(user.isEmpty()) {
            throw new EntityNotFoundException("Essa conta não foi encontrado");
        }

        this.userRepository.delete(user.get());

        DefaultResponseDTO response = new DefaultResponseDTO();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Sua conta foi deletada com sucesso");
        response.setSuccess(Boolean.TRUE);

        return response;
    }

}
