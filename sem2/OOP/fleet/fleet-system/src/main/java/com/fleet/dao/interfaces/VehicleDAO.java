package com.fleet.dao.interfaces;

import com.fleet.models.Vehicle;

import java.util.List;

public interface VehicleDAO {
    void saveVehicle(Vehicle vehicle);

    Vehicle getVehicleById(Long id);

    List<Vehicle> getAllVehicles();

    void updateVehicle(Vehicle vehicle);

    void deleteVehicle(Vehicle vehicle);
}
