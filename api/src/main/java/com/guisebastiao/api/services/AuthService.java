package com.guisebastiao.api.services;

import com.guisebastiao.api.dtos.*;
import com.guisebastiao.api.enums.Role;
import com.guisebastiao.api.exceptions.DuplicateEntityException;
import com.guisebastiao.api.exceptions.EntityNotFoundException;
import com.guisebastiao.api.exceptions.RequiredAuthenticationException;
import com.guisebastiao.api.models.User;
import com.guisebastiao.api.repositories.UserRepository;
import io.minio.MinioClient;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@NoArgsConstructor
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private MinioClient minioClient;

    public DefaultResponseDTO login(LoginDTO dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("Credenciais incorretas"));

        if(!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RequiredAuthenticationException("Credenciais incorretas");
        }

        AuthResponseDTO authResponse = tokenService.generateToken(user);
        UserResponseDTO userDTO = new UserResponseDTO();
        authResponse.setUser(userDTO.toDto(user, minioClient));

        DefaultResponseDTO response = new DefaultResponseDTO();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Login efetuado com sucesso");
        response.setData(authResponse);
        response.setSuccess(Boolean.TRUE);
        return response;
    }

    public DefaultResponseDTO register(RegisterDTO dto) {
        Optional<User> existUser = userRepository.findByEmail(dto.getEmail());

        if(existUser.isPresent()) {
            throw new DuplicateEntityException("Essa conta já esta cadastrada");
        }

        User user = new User();

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setPhone(dto.getPhone());
        user.setRole(Role.fromString(dto.getRole()));

        this.userRepository.save(user);

        AuthResponseDTO authResponse = tokenService.generateToken(user);
        UserResponseDTO userDTO = new UserResponseDTO();
        authResponse.setUser(userDTO.toDto(user, minioClient));

        DefaultResponseDTO response = new DefaultResponseDTO();
        response.setStatus(HttpStatus.CREATED.value());
        response.setMessage("Registro efetuado com sucesso");
        response.setData(authResponse);
        response.setSuccess(Boolean.TRUE);

        return response;
    }

    public DefaultResponseDTO refreshToken(RefreshTokenDTO dto) {
        String login = this.tokenService.getSubjectFromTokenEvenIfExpired(dto.getToken());

        if (login == null) {
            throw new RequiredAuthenticationException("Por favor faça o login novamente");
        }

        User user = this.userRepository.findById(UUID.fromString(login))
                .orElseThrow(() -> new RequiredAuthenticationException("Por favor faça o login novamente"));

        AuthResponseDTO authResponse = tokenService.generateToken(user);
        UserResponseDTO userDTO = new UserResponseDTO();
        authResponse.setUser(userDTO.toDto(user, minioClient));

        DefaultResponseDTO response = new DefaultResponseDTO();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Sua autenticação foi atualizada com sucesso");
        response.setData(authResponse);
        response.setSuccess(Boolean.TRUE);

        return response;
    }

}
