package com.fleet.dao;

import com.fleet.dao.interfaces.DAOAsync;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DAOAsyncImpl<Entity> implements DAOAsync<Entity> {
    private final SessionFactory sessionFactory;
    private final ExecutorService executorService;
    private final Class<Entity> entityClass; // Added entityClass field

    public DAOAsyncImpl(SessionFactory sessionFactory, Class<Entity> entityClass) {
        this.sessionFactory = sessionFactory;
        this.executorService = Executors.newFixedThreadPool(4); // Corrected method name
        this.entityClass = entityClass; // Initialize entityClass field
    }

    @Override
    public CompletableFuture<List<Entity>> getAll() {
        return CompletableFuture.supplyAsync(() -> {
            try (Session session = sessionFactory.openSession()) {
                CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
                CriteriaQuery<Entity> criteriaQuery = criteriaBuilder.createQuery(entityClass); // Use entityClass
                Root<Entity> root = criteriaQuery.from(entityClass);
                criteriaQuery.select(root);
                Query<Entity> query = session.createQuery(criteriaQuery);
                return query.getResultList();
            }
        }, executorService);
    }

    @Override
    public CompletableFuture<Entity> getById(Long id) {
        return CompletableFuture.supplyAsync(() -> {
            try (Session session = sessionFactory.openSession()) {
                return session.get(entityClass, id); // Use entityClass
            }
        }, executorService);
    }

    @Override
    public CompletableFuture<Void> save(Entity entity) {
        return CompletableFuture.runAsync(() -> {
            try (Session session = sessionFactory.openSession()) {
                Transaction transaction = session.beginTransaction();
                session.save(entity);
                transaction.commit();
            }
        }, executorService);
    }

    @Override
    public CompletableFuture<Void> update(Entity entity) {
        return CompletableFuture.runAsync(() -> {
            try (Session session = sessionFactory.openSession()) {
                Transaction transaction = session.beginTransaction();
                session.update(entity);
                transaction.commit();
            }
        }, executorService);
    }

    @Override
    public CompletableFuture<Void> delete(Long id) {
        return CompletableFuture.runAsync(() -> {
            try (Session session = sessionFactory.openSession()) {
                Transaction transaction = session.beginTransaction();
                Entity entity = session.get(entityClass, id); // Use entityClass
                session.delete(entity);
                transaction.commit();
            }
        }, executorService);
    }
}
