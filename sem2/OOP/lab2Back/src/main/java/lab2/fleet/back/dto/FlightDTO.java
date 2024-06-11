package lab2.fleet.back.dto;

import lombok.Data;
import java.util.Date;

@Data
public class FlightDTO {
    private String id;
    private Date estimatedArrivalTime;
    private Date actualDepartureTime;
    private Date actualArrivalTime;
    private String requestId; // Assuming Request has an id field of type Long
    private String driverId; // Assuming Driver has an id field of type Long
    private String vehicleId; // Assuming Vehicle has an id field of type Long
    private String userId; // Assuming User has an id field of type String
    private String status;
}