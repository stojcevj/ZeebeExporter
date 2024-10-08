package com.joci.repository.impl;

import com.joci.entites.ProcessEntity;
import org.hibernate.SessionFactory;

public class ProcessRepository extends GenericRepositoryImpl<ProcessEntity, Long> {
    public ProcessRepository(SessionFactory sessionFactory) {
        super(sessionFactory, ProcessEntity.class);
    }
}
