package com.joci.repository;

import com.joci.entites.MessageSubscriptionEntity;

import java.util.Optional;

public interface MessageSubscriptionRepository extends GenericRepository<MessageSubscriptionEntity, String> {
    Optional<MessageSubscriptionEntity> findByElementInstanceKeyAndMessageName(Long ElementInstanceKey, String MessageName);
    Optional<MessageSubscriptionEntity> findByProcessDefinitionKeyAndMessageName(Long ProcessDefinitionKey, String MessageName);
}
