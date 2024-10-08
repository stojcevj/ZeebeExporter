package com.joci.repository.impl;

import com.joci.entites.ProcessInstanceEntity;
import org.hibernate.SessionFactory;

public class ProcessInstanceRepository extends GenericRepositoryImpl<ProcessInstanceEntity, Long>{
    public ProcessInstanceRepository(SessionFactory sessionFactory) {
        super(sessionFactory, ProcessInstanceEntity.class);
    }
}
