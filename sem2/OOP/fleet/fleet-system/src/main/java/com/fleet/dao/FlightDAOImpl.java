package com.fleet.dao;

import com.fleet.models.Flight;
import com.fleet.dao.interfaces.FlightDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class FlightDAOImpl implements FlightDAO {

    private SessionFactory sessionFactory;

    public FlightDAOImpl() {
        // Initialize Hibernate SessionFactory
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
    }

    @Override
    public void saveFlight(Flight flight) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(flight);
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
    public Flight getFlightById(Long id) {
        Session session = sessionFactory.openSession();
        Flight flight = session.get(Flight.class, id);
        session.close();
        return flight;
    }

    @Override
    public List<Flight> getAllFlights() {
        Session session = sessionFactory.openSession();
        List<Flight> flights;
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Flight> criteriaQuery = builder.createQuery(Flight.class);
            Root<Flight> root = criteriaQuery.from(Flight.class);
            criteriaQuery.select(root);
            Query<Flight> query = session.createQuery(criteriaQuery);
            flights = query.getResultList();
        } finally {
            session.close();
        }
        return flights;
    }

    @Override
    public void updateFlight(Flight flight) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(flight);
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
    public void deleteFlight(Long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Flight flight = session.get(Flight.class, id);
            session.delete(flight);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
    }


}
