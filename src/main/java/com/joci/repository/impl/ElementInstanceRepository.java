package com.joci.repository.impl;

import com.joci.entites.ElementInstanceEntity;
import org.hibernate.SessionFactory;

public class ElementInstanceRepository extends GenericRepositoryImpl<ElementInstanceEntity, String> {
    public ElementInstanceRepository(SessionFactory sessionFactory) {
        super(sessionFactory, ElementInstanceEntity.class);
    }
}
