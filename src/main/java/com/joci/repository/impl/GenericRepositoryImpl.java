package com.joci.repository.impl;

import com.joci.entites.BaseEntity;
import com.joci.repository.GenericRepository;
import io.camunda.zeebe.exporter.api.context.Controller;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.io.Serializable;
import java.util.List;

public class GenericRepositoryImpl<T, ID extends Serializable> implements GenericRepository<T, ID> {
    private final SessionFactory sessionFactory;
    private final Class<T> entityClass;

    public GenericRepositoryImpl(SessionFactory sessionFactory, Class<T> entityClass) {
        this.sessionFactory = sessionFactory;
        this.entityClass = entityClass;
    }

    @Override
    public T findById(ID id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(entityClass, id);
        }
    }

    @Override
    public List<T> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from " + entityClass.getSimpleName(), entityClass).list();
        }
    }

    @Override
    public void save(T entity, ID id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            T existingEntity = session.get(entityClass, id);

            if (existingEntity == null) {
                session.persist(entity);
            } else {
                System.out.println("Entity with id " + id + " already exists.");
            }

            session.getTransaction().commit();
        }
    }


    @Override
    public void update(T entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(entity);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(T entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(entity);
            session.getTransaction().commit();
        }
    }
}
