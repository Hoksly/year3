package lab2.fleet.back.dto;

import lombok.Data;

@Data
public class RequestDTO {
    private String id;
    private String origin;
    private String userId; // Assuming User has an id field of type String
    private String destination;
    private String message;
    private String requirements;
    private Boolean isActive;
    private double fare;
    private String preferredVehicleCategory;
}