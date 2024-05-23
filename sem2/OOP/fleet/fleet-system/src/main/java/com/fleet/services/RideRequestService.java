package com.fleet.services;

import com.fleet.dao.RequestDAOImpl;
import com.fleet.dao.UserDAOImpl;
import com.fleet.models.Request;
import com.fleet.models.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class RideRequestService {
    private RequestDAOImpl rideRequestDAO;
    private UserDAOImpl userDAO;

    public RideRequestService() {
        // Initialize Hibernate SessionFactory
        Configuration configuration = new Configuration().configure();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());

        // Initialize Request       DAO
        rideRequestDAO = new RequestDAOImpl();
        userDAO = new UserDAOImpl();
    }

    public void connectRideToUser(Long rideRequestId, Long userId) {
        Request rideRequest = rideRequestDAO.getRequestById(rideRequestId);
        rideRequest.setUser(userDAO.getUserById(userId));
        rideRequestDAO.updateRequest(rideRequest);
    }

    public List<Request> getRideRequests() {
        return rideRequestDAO.getAllRequests();
    }

    public void saveRideRequest(Request rideRequest) {
        if(rideRequest.getUser() == null) {
            throw new IllegalArgumentException("Ride request must be associated with a user");
        }

        User realUser = userDAO.getUserByUsername(rideRequest.getUser().getUsername());
        if(realUser == null) {
            userDAO.saveUser(rideRequest.getUser());
            realUser = userDAO.getUserByUsername(rideRequest.getUser().getUsername());
        }

        rideRequest.setUser(realUser);
        if (rideRequest.getFare() < 0) {
            throw new IllegalArgumentException("Fare cannot be negative");
        }

        // Check if the request already exists before saving it
        if (!rideRequestDAO.doesRequestExistWithSameOriginAndDestination(rideRequest)) {
            rideRequestDAO.saveRequest(rideRequest);
        } else {
            System.out.println("A similar request already exists in the database.");
        }
    }

    private boolean rideRequestExists(Request request) {
        return rideRequestDAO.getRequestById(request.getId()) != null;
    }

    public void updateRideRequest(Request       updatedRideRequest) {
        rideRequestDAO.updateRequest(updatedRideRequest);
    }

    public void deleteRideRequest(Long rideRequestId) {
        rideRequestDAO.deleteRequest(rideRequestId);
    }
}