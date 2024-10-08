package com.joci.repository;

import io.camunda.zeebe.exporter.api.context.Controller;

import java.io.Serializable;
import java.util.List;

public interface GenericRepository<T, ID extends Serializable> {
    T findById(ID id);
    List<T> findAll();
    void save(T entity, ID id);
    void update(T entity);
    void delete(T entity);
}

