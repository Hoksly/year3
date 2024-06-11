package lab2.fleet.back.service;

import lab2.fleet.back.entity.Vehicle;
import lab2.fleet.back.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public Optional<Vehicle> getVehicleById(String id) {
        return vehicleRepository.findById(id);
    }

    public Vehicle createVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public Vehicle updateVehicle(String id, Vehicle vehicleDetails) {
        Optional<Vehicle> optionalVehicle = vehicleRepository.findById(id);
        if(optionalVehicle.isPresent()) {
            Vehicle vehicle = optionalVehicle.get();
            // Update the vehicle details here
            // For example, if Vehicle has a name field, you can do:
            // vehicle.setName(vehicleDetails.getName());
            return vehicleRepository.save(vehicle);
        } else {
            throw new RuntimeException("Vehicle not found with id: " + id);
        }
    }

    public void deleteVehicle(String id) {
        if(vehicleRepository.existsById(id)) {
            vehicleRepository.deleteById(id);
        } else {
            throw new RuntimeException("Vehicle not found with id: " + id);
        }
    }
}