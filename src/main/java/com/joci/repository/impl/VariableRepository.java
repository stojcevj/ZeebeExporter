package com.joci.repository.impl;

import com.joci.entites.VariableEntity;
import org.hibernate.SessionFactory;

public class VariableRepository extends GenericRepositoryImpl<VariableEntity, String> {
    public VariableRepository(SessionFactory sessionFactory) {
        super(sessionFactory, VariableEntity.class);
    }
}
