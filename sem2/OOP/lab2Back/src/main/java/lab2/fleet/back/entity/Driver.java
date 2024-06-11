package lab2.fleet.back.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "flights")
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "registration_date")
    private Date registrationDate;

    @Column(name = "license_number")
    private String licenseNumber;

    @Column(name = "license_state")
    private String licenseState;

    @Column(name = "license_expiry")
    private String licenseExpiry;

    @Column(name = "license_category")
    private String licenseCategory;

    @Column(name = "license_info")
    private String licenseInfo;

    @Column(name = "rating")
    private double rating;

    @Column(name = "vehicle_id")
    private String vehicleId;
}
