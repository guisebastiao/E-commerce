package com.guisebastiao.api.enums;

public enum ProductActive {
    ACTIVE,
    INACTIVE;

    public static ProductActive fromString(String value) {
        return ProductActive.valueOf(value.toUpperCase());
    }
}
