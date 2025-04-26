package com.guisebastiao.api.dtos;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class DefaultResponseDTO {
    private int status;
    private String message;
    private Object data;
    private Boolean success;
    private PagingDTO paging;
    private List<FieldErrorDTO> fieldErrors;
}
