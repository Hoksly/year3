package com.fleet.dao;

import com.fleet.models.Vehicle;
import com.fleet.models.Vehicle;
import com.fleet.dao.interfaces.VehicleDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class VehicleDAOImpl implements VehicleDAO {

    private SessionFactory sessionFactory;

    public VehicleDAOImpl() {
        // Initialize Hibernate SessionFactory
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
    }

    @Override
    public void saveVehicle(Vehicle vehicle) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(vehicle);
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

    @Override
    public Vehicle getVehicleById(Long id) {
        Session session = sessionFactory.openSession();
        Vehicle vehicle = session.get(Vehicle.class, id);
        session.close();
        return vehicle;
    }

    @Override
    public List<Vehicle> getAllVehicles() {
        Session session = sessionFactory.openSession();
        List<Vehicle> vehicles;
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Vehicle> criteriaQuery = builder.createQuery(Vehicle.class);
            Root<Vehicle> root = criteriaQuery.from(Vehicle.class);
            criteriaQuery.select(root);
            Query<Vehicle> query = session.createQuery(criteriaQuery);
            vehicles = query.getResultList();
        } finally {
            session.close();
        }
        return vehicles;
    }

    @Override
    public void updateVehicle(Vehicle vehicle) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(vehicle);
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

    @Override
    public void deleteVehicle(Vehicle vehicle) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(vehicle);
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
}
