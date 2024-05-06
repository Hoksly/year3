package com.fleet.services;

import com.fleet.dao.VehicleDAOImpl;
import com.fleet.models.Vehicle;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class VehicleService {
    private VehicleDAOImpl vehicleDAO;

    public VehicleService() {
        // Initialize Hibernate SessionFactory
        Configuration configuration = new Configuration().configure();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());

        // Initialize Vehicle DAO
        vehicleDAO = new VehicleDAOImpl(sessionFactory);
    }

    public Vehicle getVehicle(Long vehicleId) {
        return vehicleDAO.getVehicle(vehicleId);
    }

    public List<Vehicle> getVehicles() {
        return vehicleDAO.getVehicles(10);
    }

    public void saveVehicle(Vehicle vehicle) {
        vehicleDAO.saveVehicle(vehicle);
    }

    public void updateVehicle(Vehicle updatedVehicle) {
        vehicleDAO.updateVehicle(updatedVehicle);
    }

    public void deleteVehicle(Long vehicleId) {
        vehicleDAO.deleteVehicle(vehicleId);
    }
}