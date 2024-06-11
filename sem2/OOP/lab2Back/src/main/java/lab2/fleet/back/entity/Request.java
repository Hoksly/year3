package lab2.fleet.back.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "origin")
    private String origin;

    @Column(name = "user_id")
    private int user;

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
