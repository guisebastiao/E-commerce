package com.guisebastiao.api.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldErrorDTO {
    private String field;
    private String error;
}