package com.joci.mappers;

import com.joci.entites.MessageSubscriptionEntity;
import com.joci.repository.MessageSubscriptionRepository;
import io.camunda.zeebe.exporter.api.context.Controller;
import io.camunda.zeebe.protocol.record.Record;
import io.camunda.zeebe.protocol.record.value.MessageStartEventSubscriptionRecordValue;

public class MessageStartEventSubscriptionMapper {
    public static void ToMessageStartEventSubscriptionEntity(Record<MessageStartEventSubscriptionRecordValue> record,
                                                   Controller controller,
                                                   MessageSubscriptionRepository messageSubscriptionRepository) {
        MessageSubscriptionEntity entity = messageSubscriptionRepository
                .findByProcessDefinitionKeyAndMessageName(record.getValue().getProcessDefinitionKey(),
                        record.getValue().getMessageName())
                .orElseGet(() -> BaseEntityMapper
                        .MapBaseEntityNoKeyProperties(MessageSubscriptionEntity.builder(), record)
                        .Id(MessageSubscriptionEntityMapper.generateId())
                        .MessageName(record.getValue().getMessageName())
                        .TargetFlowNodeId(record.getValue().getStartEventId())
                        .ProcessDefinitionKey(record.getValue().getProcessDefinitionKey())
                        .BpmnProcessId(record.getValue().getBpmnProcessId())
                        .build());

        entity.setState(record.getIntent().name().toLowerCase());
        entity.setTimestamp(record.getTimestamp());

        messageSubscriptionRepository.save(entity, entity.getId());

        controller.updateLastExportedRecordPosition(record.getPosition());
    }
}
