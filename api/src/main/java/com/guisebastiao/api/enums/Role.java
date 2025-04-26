package com.guisebastiao.api.enums;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public enum Role {
    ADMIN,
    SELLER,
    CLIENT;

    public static Role fromString(String value) {
        return Role.valueOf(value.toUpperCase());
    }
}
