package com.joci.repository.impl;

import com.joci.entites.IncidentEntity;
import com.joci.repository.IncidentRepository;
import org.hibernate.SessionFactory;

public class IncidentRepositoryImpl
        extends GenericRepositoryImpl<IncidentEntity, Long>
        implements IncidentRepository {
    public IncidentRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory, IncidentEntity.class);
    }
}
