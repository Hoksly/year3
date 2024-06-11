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
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "estimated_arrival_time")
    private Date estimatedArrivalTime;

    @Column(name = "actual_departure_time")
    private Date actualDepartureTime;

    @Column(name = "actual_arrival_time")
    private Date actualArrivalTime;

    @Column(name = "request_id")
    private String requestId;

    @Column(name = "driver_id")
    private String driverId;

    @Column(name = "vehicle_id")
    private String vehicleId;


    @Column(name = "user_id")
    private String userId;

    @Column(name = "status")
    private String status;
}
