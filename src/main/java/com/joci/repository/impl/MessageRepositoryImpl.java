package com.joci.repository.impl;

import com.joci.entites.MessageEntity;
import com.joci.repository.MessageRepository;
import org.hibernate.SessionFactory;

public class MessageRepositoryImpl
        extends GenericRepositoryImpl<MessageEntity, Long>
        implements MessageRepository {
    public MessageRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory, MessageEntity.class);
    }
}
