package com.guisebastiao.api.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.guisebastiao.api.dtos.AuthResponseDTO;
import com.guisebastiao.api.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
public class TokenService {

    @Value("${auth.jwt.token.secret}")
    private String secretToken;

    @Value("${session.expiration.time}")
    private String durationToken;

    public AuthResponseDTO generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.secretToken);

            Instant expires = this.generateExpirationDate();

            String token = JWT.create()
                    .withIssuer("e-commerce")
                    .withSubject(user.getId().toString())
                    .withExpiresAt(expires)
                    .sign(algorithm);
            AuthResponseDTO authResponseDTO = new AuthResponseDTO();
            authResponseDTO.setToken(token);
            authResponseDTO.setExpires(expires);

            return authResponseDTO;
        } catch (JWTCreationException exception) {
            throw new JWTCreationException("JWT generation failed", exception);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.secretToken);

            return JWT.require(algorithm)
                    .withIssuer("e-commerce")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            return null;
        }
    }

    public String getSubjectFromTokenEvenIfExpired(String token) {
        try {
            DecodedJWT decodedJWT = JWT.decode(token);
            return decodedJWT.getSubject();
        } catch (JWTVerificationException e) {
            return null;
        }
    }

    private Instant generateExpirationDate() {
        int jwtDuration = Integer.parseInt(this.durationToken);
        return LocalDateTime.now().plusHours(jwtDuration).toInstant(ZoneOffset.UTC);
    }
}
