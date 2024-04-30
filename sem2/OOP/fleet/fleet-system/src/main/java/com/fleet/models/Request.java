package com.fleet.models;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "origin")
    private String origin;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "destination")
    private String destination;

    @Column(name = "message")
    private String message;

    @Column(name = "requirements")
    private String requirements;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "fare")
    private double fare;

    @Column(name = "preferred_vehicle_category")
    private String preferredVehicleCategory;
}
