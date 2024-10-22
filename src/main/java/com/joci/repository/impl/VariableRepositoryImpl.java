package com.joci.repository.impl;

import com.joci.entites.VariableEntity;
import com.joci.repository.VariableRepository;
import org.hibernate.SessionFactory;

public class VariableRepositoryImpl
        extends GenericRepositoryImpl<VariableEntity, String>
        implements VariableRepository {
    public VariableRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory, VariableEntity.class);
    }
}
