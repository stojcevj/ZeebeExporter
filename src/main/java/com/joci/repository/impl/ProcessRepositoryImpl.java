package com.joci.repository.impl;

import com.joci.entites.ProcessEntity;
import com.joci.repository.ProcessRepository;
import org.hibernate.SessionFactory;

public class ProcessRepositoryImpl
        extends GenericRepositoryImpl<ProcessEntity, Long>
        implements ProcessRepository {
    public ProcessRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory, ProcessEntity.class);
    }
}
