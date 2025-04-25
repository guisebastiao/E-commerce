package com.guisebastiao.api.security;

import com.guisebastiao.api.exceptions.RequiredAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthUtil {
    public Authentication getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || !authentication.isAuthenticated()) {
            throw new RequiredAuthenticationException("Por favor faça o login novamente");
        }

        return (Authentication) authentication.getPrincipal();
    }
}
