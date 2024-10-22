package com.joci.repository.impl;

import com.joci.entites.ElementInstanceEntity;
import com.joci.repository.ElementInstanceRepository;
import org.hibernate.SessionFactory;

public class ElementInstanceRepositoryImpl
        extends GenericRepositoryImpl<ElementInstanceEntity, String>
        implements ElementInstanceRepository {
    public ElementInstanceRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory, ElementInstanceEntity.class);
    }
}
