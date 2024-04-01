package com.fleet.servlets;

import com.fleet.dao.DAOAsyncImpl;
import com.fleet.models.Vehicle;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@WebServlet(name = "VehicleServlet", urlPatterns = "/vehicles")
public class VehicleServlet extends HttpServlet {
    private DAOAsyncImpl<Vehicle> vehicleDAO;

    @Override
    public void init() throws ServletException {
        // Initialize Hibernate SessionFactory
        Configuration configuration = new Configuration().configure();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());

        // Initialize Vehicle DAO
        vehicleDAO = new DAOAsyncImpl<>(sessionFactory, Vehicle.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle GET request to retrieve all vehicles
        CompletableFuture<List<Vehicle>> future = vehicleDAO.getAll();
        future.thenAccept(vehicles -> {
            // Convert vehicles list to JSON or any other format you prefer
            // Send the response back to the client
        });
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle POST request to create a new vehicle
        // Parse request parameters and create a new Vehicle object
        Vehicle vehicle = new Vehicle(/* parse request parameters */);

        // Save the new vehicle asynchronously
        CompletableFuture<Void> future = vehicleDAO.save(vehicle);
        future.thenRun(() -> {
            // Vehicle saved successfully
            // Send a success response back to the client
        });
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle PUT request to update an existing vehicle
        // Parse request parameters and update the corresponding Vehicle object
        Vehicle updatedVehicle = new Vehicle(/* parse request parameters */);

        // Update the vehicle asynchronously
        CompletableFuture<Void> future = vehicleDAO.update(updatedVehicle);
        future.thenRun(() -> {
            // Vehicle updated successfully
            // Send a success response back to the client
        });
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle DELETE request to delete an existing vehicle
        // Parse request parameters to get the ID of the vehicle to be deleted
        Long vehicleId = Long.parseLong(request.getParameter("id"));

        // Delete the vehicle asynchronously
        CompletableFuture<Void> future = vehicleDAO.delete(vehicleId);
        future.thenRun(() -> {
            // Vehicle deleted successfully
            // Send a success response back to the client
        });
    }

    @Override
    public void destroy() {
        // Close the Hibernate SessionFactory when the servlet is destroyed
    }
}
