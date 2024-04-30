package com.fleet.models;

import lombok.Data;
public class Vehicle {
    private Long id;
    private String type; // Sedan, SUV, Truck, etc.
    private String condition; // Excellent, Good, Fair, etc.
    private String indicators; // Could store as JSON if you have complex indicators

    // Constructors (Lombok can generate these, but shown for clarity)
    public Vehicle() {}

    public Vehicle(Long id, String type, String condition, String indicators) {
        this.id = id;
        this.type = type;
        this.condition = condition;
        this.indicators = indicators;
    }
}
