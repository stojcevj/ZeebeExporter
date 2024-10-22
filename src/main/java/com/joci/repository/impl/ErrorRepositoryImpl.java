package com.joci.repository.impl;

import com.joci.entites.ErrorEntity;
import com.joci.repository.ErrorRepository;
import org.hibernate.SessionFactory;

public class ErrorRepositoryImpl
        extends GenericRepositoryImpl<ErrorEntity, Long>
        implements ErrorRepository {
    public ErrorRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory, ErrorEntity.class);
    }
}
