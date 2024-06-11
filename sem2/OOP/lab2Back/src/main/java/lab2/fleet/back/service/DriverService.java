package lab2.fleet.back.service;

import lab2.fleet.back.entity.Driver;
import lab2.fleet.back.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DriverService {

    @Autowired
    private DriverRepository driverRepository;

    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    public Optional<Driver> getDriverById(String id) {
        return driverRepository.findById(id);
    }

    public Driver createDriver(Driver driver) {
        return driverRepository.save(driver);
    }

    public Driver updateDriver(String id, Driver driverDetails) {
        Optional<Driver> optionalDriver = driverRepository.findById(id);
        if(optionalDriver.isPresent()) {
            Driver driver = optionalDriver.get();
            // Update the driver details here
            // For example, if Driver has a name field, you can do:
            // driver.setName(driverDetails.getName());
            return driverRepository.save(driver);
        } else {
            throw new RuntimeException("Driver not found with id: " + id);
        }
    }

    public void deleteDriver(String id) {
        if(driverRepository.existsById(id)) {
            driverRepository.deleteById(id);
        } else {
            throw new RuntimeException("Driver not found with id: " + id);
        }
    }
}