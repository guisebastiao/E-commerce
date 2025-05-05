package com.guisebastiao.api.services;

import com.guisebastiao.api.dtos.DefaultResponseDTO;
import com.guisebastiao.api.dtos.UserDTO;
import com.guisebastiao.api.dtos.UserUpdateDTO;
import com.guisebastiao.api.exceptions.EntityNotFoundException;
import com.guisebastiao.api.models.User;
import com.guisebastiao.api.repositories.UserRepository;
import com.guisebastiao.api.utils.UUIDConverter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public DefaultResponseDTO getUserById(String id) {
        User user = this.userRepository.findById(UUIDConverter.toUUID(id))
                .orElseThrow(() -> new EntityNotFoundException("Essa conta não foi encontrado"));

        UserDTO userDTO = new UserDTO();

        DefaultResponseDTO response = new DefaultResponseDTO();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Conta encontrada com sucesso");
        response.setData(userDTO.toDto(user));
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
