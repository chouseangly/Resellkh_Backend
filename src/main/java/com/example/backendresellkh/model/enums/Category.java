package com.example.backendresellkh.model.enums;

import java.util.Arrays;

public enum Category {
    ACCESSORIES(1, "Accessories"),
    BEAUTY(2, "Beauty"),
    EQUIPMENT_BAG_SHOES(3, "Equipment Bag & Shoes"),
    BOOK(4, "Book"),
    FASHION(5, "Fashion"),
    HOME(6, "Home"),
    SPORTS_KIDS(7, "Sports & Kids"),
    ELECTRONIC(8, "Electronic"),
    VEHICLE(9, "Vehicle"),
    OTHER(10, "Other");

    private final int id;
    private final String name;

    Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static Category fromId(int id) {
        return Arrays.stream(Category.values())
                .filter(c -> c.id == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID: " + id));
    }

    public static Category fromName(String name) {
        return Arrays.stream(Category.values())
                .filter(c -> c.name.equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid category name: " + name));
    }
}
