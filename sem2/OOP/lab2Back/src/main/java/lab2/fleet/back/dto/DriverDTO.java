package lab2.fleet.back.dto;

import lombok.Data;
import java.util.Date;

@Data
public class DriverDTO {
    private String id;
    private String name;
    private String email;
    private String phone;
    private Date registrationDate;
    private String licenseNumber;
    private String licenseState;
    private String licenseExpiry;
    private String licenseCategory;
    private String licenseInfo;
    private double rating;
    private Long assignedVehicleId; // Assuming Vehicle has an id field of type Long
}