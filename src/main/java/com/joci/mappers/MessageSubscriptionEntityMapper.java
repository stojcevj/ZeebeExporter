package com.joci.mappers;

import com.joci.entites.MessageSubscriptionEntity;
import com.joci.repository.MessageSubscriptionRepository;
import io.camunda.zeebe.exporter.api.context.Controller;
import io.camunda.zeebe.protocol.record.Record;
import io.camunda.zeebe.protocol.record.value.MessageSubscriptionRecordValue;

import java.util.UUID;

public class MessageSubscriptionEntityMapper {
    public static void ToMessageSubscriptionEntity(Record<MessageSubscriptionRecordValue> record,
                                        Controller controller,
                                        MessageSubscriptionRepository messageSubscriptionRepository) {
        MessageSubscriptionEntity entity = messageSubscriptionRepository
                .findByElementInstanceKeyAndMessageName(record.getValue().getElementInstanceKey(),
                        record.getValue().getMessageName())
                .orElseGet(() -> BaseEntityMapper
                                .MapBaseEntityNoKeyProperties(MessageSubscriptionEntity.builder(), record)
                                .Id(generateId())
                                .MessageName(record.getValue().getMessageName())
                                .ElementInstanceKey(record.getValue().getElementInstanceKey())
                                .CorrelationKey(record.getValue().getCorrelationKey())
                                .ProcessInstanceKey(record.getValue().getProcessInstanceKey())
                                .IsInterrupting(record.getValue().isInterrupting())
                                .BpmnProcessId(record.getValue().getBpmnProcessId())
                                .build());

        entity.setState(record.getIntent().name().toLowerCase());
        entity.setTimestamp(record.getTimestamp());

        messageSubscriptionRepository.save(entity, entity.getId());

        controller.updateLastExportedRecordPosition(record.getPosition());
    }

    public static String generateId() {
        return UUID.randomUUID().toString();
    }
}
