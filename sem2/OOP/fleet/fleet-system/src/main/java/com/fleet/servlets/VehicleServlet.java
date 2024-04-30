package com.fleet.servlets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fleet.dao.DAOAsyncImpl;
import com.fleet.dao.VehicleDAOImpl;
import com.fleet.models.Vehicle;
import com.fleet.resolvers.CustomKeycloakConfigResolver;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.TokenVerifier;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.common.VerificationException;
import org.keycloak.representations.AccessToken;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.List;


@WebServlet(name = "VehicleServlet", urlPatterns = "/vehicles")
public class VehicleServlet extends HttpServlet {
    private VehicleDAOImpl vehicleDAO;

    @Override
    public void init() throws ServletException {
        // Initialize Hibernate SessionFactory
        Configuration configuration = new Configuration().configure();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());

        // Initialize Vehicle DAO
        vehicleDAO = new VehicleDAOImpl(sessionFactory);
    }

    public PublicKey getPublicKeyFromKeycloak() throws IOException {
        URL url = new URL("http://localhost:8079/realms/fleet-realm");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        JsonReader jsonReader = Json.createReader(reader);
        JsonObject jsonObject = jsonReader.readObject();
        String base64PublicKey = jsonObject.getString("public_key");
        try{
            byte[] byteKey = Base64.getDecoder().decode(base64PublicKey.getBytes());
            X509EncodedKeySpec X509publicKey = new X509EncodedKeySpec(byteKey);
            KeyFactory kf = KeyFactory.getInstance("RSA");

            return kf.generatePublic(X509publicKey);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }


    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String jwt = authHeader.substring(7); // Remove "Bearer " prefix

        // Validate the JWT
        try {
            PublicKey publicKey = getPublicKeyFromKeycloak();

            TokenVerifier<AccessToken> verifier = TokenVerifier.create(jwt, AccessToken.class).withChecks(TokenVerifier.IS_ACTIVE);
            verifier.publicKey(publicKey);
            AccessToken token = verifier.verify().getToken();


        }

        catch (VerificationException e) {
            System.out.println("Not authorized");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }


        System.out.println("GET request received");
        try {
            System.out.println("Getting all vehicles");
            List<Vehicle> vehicles = vehicleDAO.getVehicles(10);



                String jsonResponse = convertToJson(vehicles);
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


        vehicleDAO.saveVehicle(vehicle);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle PUT request to update an existing vehicle
        // Parse request parameters and update the corresponding Vehicle object
        Vehicle updatedVehicle = new Vehicle(/* parse request parameters */);

        // Update the vehicle asynchronously
        vehicleDAO.updateVehicle(updatedVehicle);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle DELETE request to delete an existing vehicle
        // Parse request parameters to get the ID of the vehicle to be deleted
        Long vehicleId = Long.parseLong(request.getParameter("id"));

    }

    @Override
    public void destroy() {
        // Close the Hibernate SessionFactory when the servlet is destroyed
    }
}
