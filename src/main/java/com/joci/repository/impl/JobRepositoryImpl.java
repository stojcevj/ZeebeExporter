package com.joci.repository.impl;

import com.joci.entites.JobEntity;
import com.joci.repository.JobRepository;
import org.hibernate.SessionFactory;

public class JobRepositoryImpl
        extends GenericRepositoryImpl<JobEntity, Long>
        implements JobRepository {
    public JobRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory, JobEntity.class);
    }
}
