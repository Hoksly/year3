package com.fleet.utils;

import com.fleet.models.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Table;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.*;

public class DataInitializer {
    public static void initializeData() {

        List<User> users = initializeUsers();
        List<Vehicle> vehicles = initializeVehicles();
        List<Driver> drivers = initializeDriver(vehicles);
        List<Request> requests = initializeRequests(users);
        List<Flight> flights = initializeFlights(requests, drivers);

        saveDataToHibernate(users, vehicles, drivers, requests, flights);
        // Save the data to the database

    }private static void saveDataToHibernate(List<User> users, List<Vehicle> vehicles, List<Driver> drivers, List<Request> requests, List<Flight> flights) {
        // Create a session factory
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        // Connect to the database
        Session session     = sessionFactory.openSession();

        // Begin transaction
        Transaction transaction = session.beginTransaction();

        try {


            // Save users
            for (User user : users) {
                session.save(user);
            }

            // Save vehicles
            for (Vehicle vehicle : vehicles) {
                session.save(vehicle);
            }

            // Save drivers
            for (Driver driver : drivers) {
                session.save(driver);
            }

            // Save requests
            for (Request request : requests) {
                session.save(request);
            }

            // Save flights
            for (Flight flight : flights) {
                session.save(flight);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    private static List<Flight> initializeFlights(List<Request> requests, List<Driver> drivers) {
        List<Flight> flights = new ArrayList<>();

            flights.add(createDummyFlight(new Date(1711282800000L), new Date(1711284000000L), new Date(1711284180000L), requests.get(3), drivers.get(0), drivers.get(0).getAssignedVehicle(), "Completed"));
            flights.add(createDummyFlight(new Date(1711282800000L), new Date(1711284000000L), new Date(1711284180000L), requests.get(4), drivers.get(1), drivers.get(1).getAssignedVehicle(), "Active"));


            return flights;
    }

    private static Flight createDummyFlight(Date actualDepartureTime, Date estimatedArrivalTime, Date actualArrivalTime, Request request, Driver driver, Vehicle vehicle, String status) {
        Flight flight = new Flight();
        flight.setEstimatedArrivalTime(estimatedArrivalTime);
        flight.setActualDepartureTime(actualDepartureTime);
        flight.setActualArrivalTime(actualArrivalTime);
        flight.setRequest(request);
        flight.setDriver(driver);
        flight.setVehicle(vehicle);
        flight.setStatus(status);
        // You can set other properties as needed
        return flight;
    }

    private static List<Request> initializeRequests(List<User> users) {
        List<Request> requestList = new ArrayList<>();
        requestList.add(createDummyRequest("San Francisco", users.get(0), "Los Angeles", "Need a ride to LA", "None", true, 100.0, "Comfort"));
        requestList.add(createDummyRequest("New York", users.get(1), "Boston", "Need a ride to Boston", "None", true, 150.0, "Economy"));
        requestList.add(createDummyRequest("Dallas", users.get(2), "Houston", "Need a ride to Houston", "None", true, 200.0, "Business"));
        requestList.add(createDummyRequest("Miami", users.get(3), "Orlando", "Need a ride to Orlando", "None", false, 250.0, "Comfort"));
        requestList.add(createDummyRequest("Seattle", users.get(4), "Portland", "Need a ride to Portland", "None", false, 300.0, "Economy"));

        return requestList;
    }

    public static Request createDummyRequest(String origin, User user, String destination, String message, String requirements, Boolean isActive, double fare, String preferredVehicleCategory) {
        Request request = new Request();
        request.setOrigin(origin);
        request.setUser(user);
        request.setDestination(destination);
        request.setMessage(message);
        request.setRequirements(requirements);
        request.setIsActive(isActive);
        request.setFare(fare);
        request.setPreferredVehicleCategory(preferredVehicleCategory);

        return request;
    }


    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Configuration configuration = new Configuration();

// Load configuration from hibernate.cfg.xml (or any other configuration file)
        configuration.configure("hibernate.cfg.xml");

// Build a StandardServiceRegistry using the Configuration
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();

// Build MetadataSources from the Configuration
        Metadata metadata = new MetadataSources(serviceRegistry)
                .addAnnotatedClass(com.fleet.models.Driver.class)
                .addAnnotatedClass(com.fleet.models.Vehicle.class)
                .addAnnotatedClass(com.fleet.models.Request.class)
                .addAnnotatedClass(com.fleet.models.Flight.class)
                .addAnnotatedClass(com.fleet.models.User.class)
                .buildMetadata();

// Iterate over entity mappings and inspect table and column metadata
        for (org.hibernate.mapping.PersistentClass persistentClass : metadata.getEntityBindings()) {
            String entityName = persistentClass.getEntityName();
            org.hibernate.mapping.Table table = persistentClass.getTable();
            String tableName = table.getName();
            System.out.println("Entity: " + entityName + ", Table: " + tableName);
            java.util.Iterator<org.hibernate.mapping.Column> iter = table.getColumnIterator();
            while (iter.hasNext()) {
                org.hibernate.mapping.Column column = iter.next();
                System.out.println("    Column: " + column.getName() + ", Type: " + column.getSqlType());
            }
        }

// Shutdown the ServiceRegistry
        StandardServiceRegistryBuilder.destroy(serviceRegistry);

        initializeData();
    }

    public static List<Driver> initializeDriver(List<Vehicle> vehicles) {
        List<Driver> driverList = new ArrayList<>();

        driverList.add(createDummyDriver("John Doe", "john@example.com", "1234567890", new Date(), "12345", "CA", "2025-01-01", "A", "Additional info for John", 4.5, vehicles.get(0)));
        driverList.add(createDummyDriver("Jane Smith", "jane@example.com", "0987654321", new Date(), "54321", "NY", "2024-12-31", "B", "Additional info for Jane", 4.2, vehicles.get(1)));
        driverList.add(createDummyDriver("Alice Johnson", "alice@example.com", "5555555555", new Date(), "67890", "TX", "2024-12-30", "C", "Additional info for Alice", 4.0,vehicles.get(2)));
        driverList.add(createDummyDriver("Bob Brown", "bob@example.com", "6666666666", new Date(), "13579", "FL", "2025-02-01", "A", "Additional info for Bob", 4.3, vehicles.get(3)));
        driverList.add(createDummyDriver("Emily Davis", "emily@example.com", "7777777777", new Date(), "24680", "WA", "2025-03-01", "B", "Additional info for Emily", 4.1, vehicles.get(4)));

        return driverList;
    }
    private static Driver createDummyDriver(String name, String email, String phone, Date registrationDate, String licenseNumber, String licenseState, String licenseExpiry, String licenseCategory, String licenseInfo, double rating, Vehicle vehicle) {
        Driver driver = new Driver();
        driver.setName(name);
        driver.setEmail(email);
        driver.setPhone(phone);
        driver.setRegistrationDate(registrationDate);
        driver.setLicenseNumber(licenseNumber);
        driver.setLicenseState(licenseState);
        driver.setLicenseExpiry(licenseExpiry);
        driver.setLicenseCategory(licenseCategory);
        driver.setLicenseInfo(licenseInfo);
        driver.setRating(rating);

        driver.setAssignedVehicle(vehicle);
        return driver;
    }
    public static List<User> initializeUsers() {
        List<User> userList = new ArrayList<>();

        userList.add(createDummyUser("user1", "JohnDoe", "password1", "john@example.com", "1234567890"));
        userList.add(createDummyUser("user2", "JaneSmith", "password2", "jane@example.com", "0987654321"));
        userList.add(createDummyUser("user3", "AliceJohnson", "password3", "alice@example.com", "5555555555"));
        userList.add(createDummyUser("user4", "BobBrown", "password4", "bob@example.com", "6666666666"));
        userList.add(createDummyUser("user5", "EmilyDavis", "password5", "emily@example.com", "7777777777"));

        return userList;
    }

    // Method to create a dummy user
    private static User createDummyUser(String userId, String username, String password, String email, String phoneNumber) {
        User user = new User();
        user.setUserId(userId);
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        // You can set other properties as needed
        return user;
    }

    // Method to initialize dummy vehicles
    public static List<Vehicle> initializeVehicles() {
        List<Vehicle> vehicleList = new ArrayList<>();

        vehicleList.add(createDummyVehicle("Toyota Camry", "Toyota", "ABC123", "Comfort", 2022, "Black", 10000, 2000, true, true, false, "Excellent condition", true, false, "Additional info for vehicle 1"));
        vehicleList.add(createDummyVehicle("Honda Civic", "Honda", "XYZ456", "Economy", 2021, "White", 15000, 2500, true, true, false, "Good condition", true, false, "Additional info for vehicle 2"));
        vehicleList.add(createDummyVehicle("Ford Explorer", "Ford", "DEF789", "Comfort", 2020, "Blue", 20000, 3000, true, true, true, "Fair condition", true, false, "Additional info for vehicle 3"));
        vehicleList.add(createDummyVehicle("Chevrolet Tahoe", "Chevrolet", "GHI101", "Comfort", 2023, "Silver", 5000, 1000, true, true, true, "Excellent condition", true, false, "Additional info for vehicle 4"));
        vehicleList.add(createDummyVehicle("Nissan Altima", "Nissan", "JKL202", "Business", 2024, "Red", 8000, 1500, true, true, false, "Good condition", true, false, "Additional info for vehicle 5"));

        return vehicleList;
    }

    // Method to create a dummy vehicle
    private static Vehicle createDummyVehicle(String model, String make, String plateNumber, String vehicleCategory, int year, String color, int mileage, int lastService, boolean hasAirbags, boolean hasABS, boolean isWheelchairAccessible, String condition, boolean petFriendly, boolean childSeatRequired, String info) {
        Vehicle vehicle = new Vehicle();
        vehicle.setModel(model);
        vehicle.setMake(make);
        vehicle.setPlateNumber(plateNumber);
        vehicle.setVehicleCategory(vehicleCategory);
        vehicle.setYear(year);
        vehicle.setColor(color);
        vehicle.setMileage(mileage);
        vehicle.setLastService(lastService);
        vehicle.setHasAirbags(hasAirbags);
        vehicle.setHasABS(hasABS);
        vehicle.setWheelchairAccessible(isWheelchairAccessible);
        vehicle.setCondition(condition);
        vehicle.setPetFriendly(petFriendly);
        vehicle.setChildSeatRequired(childSeatRequired);
        vehicle.setInfo(info);
        // You can set other properties as needed
        return vehicle;
    }



}
