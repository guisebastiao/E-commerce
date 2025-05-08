package com.guisebastiao.api.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class AuthResponseDTO {
    private String token;
    private Instant expires;
    private UserResponseDTO user;
}
