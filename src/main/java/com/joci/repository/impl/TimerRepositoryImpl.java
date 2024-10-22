package com.joci.repository.impl;

import com.joci.entites.TimerEntity;
import com.joci.repository.TimerRepository;
import org.hibernate.SessionFactory;

public class TimerRepositoryImpl
        extends GenericRepositoryImpl<TimerEntity, Long>
        implements TimerRepository {
    public TimerRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory, TimerEntity.class);
    }
}
