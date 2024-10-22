package com.joci.repository.impl;

import com.joci.entites.MessageSubscriptionEntity;
import com.joci.repository.MessageSubscriptionRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Optional;

public class MessageSubscriptionRepositoryImpl
        extends GenericRepositoryImpl<MessageSubscriptionEntity, String>
        implements MessageSubscriptionRepository {
    private final SessionFactory sessionFactory;
    private final Class<MessageSubscriptionEntity> entityClass;

    public MessageSubscriptionRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory, MessageSubscriptionEntity.class);
        this.sessionFactory = sessionFactory;
        this.entityClass = MessageSubscriptionEntity.class;
    }

    public Optional<MessageSubscriptionEntity> findByElementInstanceKeyAndMessageName(Long ElementInstanceKey, String MessageName) {
        try (Session session = sessionFactory.openSession()) {
            String query = "FROM " + entityClass.getSimpleName() +" ms WHERE ms.ElementInstanceKey = :ElementInstanceKey AND ms.MessageName = :MessageName";
            return Optional.ofNullable(session.createQuery(query, MessageSubscriptionEntity.class)
                    .setParameter("ElementInstanceKey", ElementInstanceKey)
                    .setParameter("MessageName", MessageName)
                    .uniqueResult());
        }
    }

    public Optional<MessageSubscriptionEntity> findByProcessDefinitionKeyAndMessageName(Long ProcessDefinitionKey, String MessageName) {
        try (Session session = sessionFactory.openSession()) {
            String query = "FROM " + entityClass.getSimpleName() +" ms WHERE ms.ProcessDefinitionKey = :ProcessDefinitionKey AND ms.MessageName = :MessageName";
            return Optional.ofNullable(session.createQuery(query, MessageSubscriptionEntity.class)
                    .setParameter("ProcessDefinitionKey", ProcessDefinitionKey)
                    .setParameter("MessageName", MessageName)
                    .uniqueResult());
        }
    }
}