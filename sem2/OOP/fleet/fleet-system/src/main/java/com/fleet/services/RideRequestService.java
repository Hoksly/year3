package com.fleet.services;

import com.fleet.dao.RequestDAOImpl;
import com.fleet.models.Request;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class RideRequestService {
    private RequestDAOImpl rideRequestDAO;

    public RideRequestService() {
        // Initialize Hibernate SessionFactory
        Configuration configuration = new Configuration().configure();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());

        // Initialize Request       DAO
        rideRequestDAO = new RequestDAOImpl();
    }

    public List<Request> getRideRequests() {
        return rideRequestDAO.getAllRequests();
    }

    public void saveRideRequest(Request rideRequest) {
        rideRequestDAO.saveRequest(rideRequest);
    }

    public void updateRideRequest(Request       updatedRideRequest) {
        rideRequestDAO.updateRequest(updatedRideRequest);
    }

    public void deleteRideRequest(Long rideRequestId) {
        rideRequestDAO.deleteRequest(rideRequestId);
    }
}