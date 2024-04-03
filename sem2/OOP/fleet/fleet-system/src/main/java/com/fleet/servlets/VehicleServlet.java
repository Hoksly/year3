package com.fleet.servlets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fleet.dao.DAOAsyncImpl;
import com.fleet.models.Vehicle;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import jakarta.servlet.Servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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
        System.out.println("GET request received");
        try {
            CompletableFuture<List<Vehicle>> future = vehicleDAO.getAll();
            future.thenAccept(vehicles -> {
                // Convert vehicles list to JSON or any other format you prefer
                String jsonResponse = convertToJson(vehicles);

                // Set response content type to JSON
                response.setContentType("application/json");

                // Write JSON response to the client
                try (PrintWriter out = response.getWriter()) {
                    out.println(jsonResponse);
                } catch (IOException e) {
                    // Handle IO exception
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    e.printStackTrace(); // Log the exception
                }
            });
        } catch (Exception e) {
            // Handle any exception that occurred during the database operation
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace(); // Log the exception
        }
    }

    // Helper method to convert list of vehicles to JSON format
    private String convertToJson(List<Vehicle> vehicles) {
        // Use a JSON library (e.g., Jackson, Gson) to convert vehicles list to JSON
        // Here's an example using Jackson ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(vehicles);
        } catch (JsonProcessingException e) {
            // Handle JSON processing exception
            e.printStackTrace(); // Log the exception
            return "[]"; // Return empty JSON array as fallback
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle POST request to create a new vehicle
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
