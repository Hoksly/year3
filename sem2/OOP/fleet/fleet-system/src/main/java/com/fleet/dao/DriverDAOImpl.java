package com.fleet.dao;

import com.fleet.models.Driver;
import com.fleet.dao.interfaces.DriverDAO;
import com.fleet.models.Driver;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class DriverDAOImpl implements DriverDAO {

    private SessionFactory sessionFactory;

    public DriverDAOImpl() {
        // Initialize Hibernate SessionFactory
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
    }

    @Override
    public void saveDriver(Driver driver) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(driver);
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
    public Driver getDriverById(Long id) {
        Session session = sessionFactory.openSession();
        Driver driver = session.get(Driver.class, id);
        session.close();
        return driver;
    }

    @Override
    public List<Driver> getAllDrivers() {
        Session session = sessionFactory.openSession();
        List<Driver> drivers;
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Driver> criteriaQuery = builder.createQuery(Driver.class);
            Root<Driver> root = criteriaQuery.from(Driver.class);
            criteriaQuery.select(root);
            Query<Driver> query = session.createQuery(criteriaQuery);
            drivers = query.getResultList();
        } finally {
            session.close();
        }
        return drivers;
    }

    @Override
    public void updateDriver(Driver driver) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(driver);
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
    public void deleteDriver(Long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Driver driver = session.get(Driver.class, id);
            session.delete(driver);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
    }

}
