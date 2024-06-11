package lab2.fleet.back.dto;


import lombok.Data;

@Data
public class VehicleDTO {
    private String id;
    private String model;
    private String make;
    private String plateNumber;
    private String vehicleCategory;
    private int year;
    private String color;
    private int mileage;
    private int lastService;
    private boolean hasAirbags;
    private boolean hasABS;
    private boolean isWheelchairAccessible;
    private String condition;
    private boolean petFriendly;
    private boolean childSeatRequired;
    private String info;
}