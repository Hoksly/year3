package com.fleet.servlets;

import com.fleet.services.AuthService;
import com.fleet.services.JsonService;
import com.fleet.services.VehicleService;
import com.fleet.models.Vehicle;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "VehicleServlet", urlPatterns = "/vehicles")
public class VehicleServlet extends HttpServlet {
    private VehicleService vehicleService;
    private JsonService jsonService;

    @Override
    public void init() throws ServletException {
        vehicleService = new VehicleService();
        jsonService = new JsonService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("GET request received");
        try {
            System.out.println("Getting all vehicles");
            List<Vehicle> vehicles = vehicleService.getVehicles();

            String jsonResponse = jsonService.convertToJson(vehicles);
            System.out.println("Converting to JSON");
            // Set response content type to JSON
            response.setContentType("application/json");

            // Write JSON response to the client
            try (PrintWriter out = response.getWriter()) {
                System.out.println("Sending response");
                System.out.println(jsonResponse);
                out.println(jsonResponse);
            } catch (IOException e) {
                System.out.println("Error sending response");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                e.printStackTrace(); // Log the exception
            }

            System.out.println("Request processed");
        } catch (Exception e) {
            // Handle any exception that occurred during the database operation
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace(); // Log the exception
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle POST request to create a new vehicle
        Vehicle vehicle = new Vehicle(/* parse request parameters */);
        vehicleService.saveVehicle(vehicle);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle PUT request to update an existing vehicle
        // Parse request parameters and update the corresponding Vehicle object
        Vehicle updatedVehicle = new Vehicle(/* parse request parameters */);
        vehicleService.updateVehicle(updatedVehicle);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle DELETE request to delete an existing vehicle
        // Parse request parameters to get the ID of the vehicle to be deleted
        Long vehicleId = Long.parseLong(request.getParameter("id"));
        vehicleService.deleteVehicle(vehicleId);
    }
}