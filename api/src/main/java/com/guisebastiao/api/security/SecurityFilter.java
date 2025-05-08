package com.guisebastiao.api.security;

import com.guisebastiao.api.models.User;
import com.guisebastiao.api.repositories.UserRepository;
import com.guisebastiao.api.services.TokenService;
import com.guisebastiao.api.utils.UUIDConverter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor

public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = this.recoverToken(request, response);
        String login = this.tokenService.validateToken(token);

        if (login != null) {
            Optional<User> user = this.userRepository.findById(UUIDConverter.toUUID(login));

            if (user.isEmpty()) {
                chain.doFilter(request, response);
                return;
            }

            var authentication = new UsernamePasswordAuthenticationToken(user.get(), null, user.get().getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request, HttpServletResponse response) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.split(" ")[0].equals("Bearer")) {
            return null;
        }

        return authHeader.split(" ")[1];
    }
}
