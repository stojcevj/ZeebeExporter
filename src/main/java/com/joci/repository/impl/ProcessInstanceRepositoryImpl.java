package com.joci.repository.impl;

import com.joci.entites.ProcessInstanceEntity;
import com.joci.repository.ProcessInstanceRepository;
import org.hibernate.SessionFactory;

public class ProcessInstanceRepositoryImpl
        extends GenericRepositoryImpl<ProcessInstanceEntity, Long>
        implements ProcessInstanceRepository {
    public ProcessInstanceRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory, ProcessInstanceEntity.class);
    }
}
