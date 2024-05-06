package com.fleet.dao;

import com.fleet.models.Request;
import com.fleet.models.Request;
import com.fleet.dao.interfaces.RequestDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class RequestDAOImpl implements RequestDAO {

    private SessionFactory sessionFactory;

    public RequestDAOImpl() {
        // Initialize Hibernate SessionFactory
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
    }

    @Override
    public void saveRequest(Request request) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(request);
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
    public Request getRequestById(Long id) {
        Session session = sessionFactory.openSession();
        Request request = session.get(Request.class, id);
        session.close();
        return request;
    }

    @Override
    public List<Request> getAllRequests() {
        Session session = sessionFactory.openSession();
        List<Request> requests;
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Request> criteriaQuery = builder.createQuery(Request.class);
            Root<Request> root = criteriaQuery.from(Request.class);
            criteriaQuery.select(root);
            Query<Request> query = session.createQuery(criteriaQuery);
            requests = query.getResultList();
        } finally {
            session.close();
        }
        return requests;
    }

    @Override
    public void updateRequest(Request request) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(request);
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
    public void deleteRequest(Long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Request request = session.get(Request.class, id);
            session.delete(request);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

    }
}
