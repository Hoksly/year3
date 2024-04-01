package com.fleet.dao.interfaces;

import com.fleet.models.Driver;

import java.util.List;

public interface DriverDAO {
    void saveDriver(Driver driver);

    Driver getDriverById(Long id);

    List<Driver> getAllDrivers();

    void updateDriver(Driver driver);

    void deleteDriver(Driver driver);
}
