package com.fleet.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vehicles")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "model")
    private String model;

    @Column(name = "make")
    private String make;

    @Column(name = "plate_number")
    private String plateNumber;

    @Column(name = "vehicle_category")
    private String vehicleCategory;

    @Column(name = "year")
    private int year;

    @Column(name = "color")
    private String color;

    @Column(name = "mileage")
    private int mileage;

    @Column(name = "last_service")
    private int lastService;

    @Column(name = "has_airbags")
    private boolean hasAirbags;

    @Column(name = "has_abs")
    private boolean hasABS;

    @Column(name = "is_wheelchair_accessible")
    private boolean isWheelchairAccessible;

    @Column(name = "condition")
    private String condition;

    @Column(name = "pet_friendly")
    private boolean petFriendly;

    @Column(name = "child_seat_required")
    private boolean childSeatRequired;

    @Column(name = "info")
    private String info;
}

