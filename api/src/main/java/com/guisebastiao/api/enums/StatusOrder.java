package com.guisebastiao.api.enums;

public enum StatusOrder {
    Pending,
    Paid,
    Sent,
    Canceled;

    public static StatusOrder fromString(String value) {
        return StatusOrder.valueOf(value.toUpperCase());
    }
}
