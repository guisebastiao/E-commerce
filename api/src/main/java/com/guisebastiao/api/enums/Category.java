package com.guisebastiao.api.enums;

public enum Category {
    ELECTRONICS,
    CLOTHING,
    BOOKS,
    FURNITURE,
    SPORTS,
    FOOD,
    TOYS,
    COSMETICS,
    TOOLS,
    AUTOMOTIVE,
    COMPUTING,
    PHONES,
    GAMES,
    MUSIC,
    HEALTH,
    DRINKS;

    public static Category fromString(String value) {
        return Category.valueOf(value.toUpperCase());
    }
}
