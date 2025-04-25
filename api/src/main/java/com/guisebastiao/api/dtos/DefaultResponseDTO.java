package com.guisebastiao.api.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@Getter
@Setter
public class DefaultResponseDTO {
    private int status;
    private String message;
    private Object data;
    private Boolean success;
    private Boolean isAuthenticated;
    private PagingDTO paging;
    private List<FieldErrorDTO> fieldErrors;

    public DefaultResponseDTO() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        this.isAuthenticated = authentication != null && authentication.isAuthenticated();
    }
}
