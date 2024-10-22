package com.joci.mappers;

import com.joci.entites.MessageEntity;
import com.joci.repository.MessageRepository;
import io.camunda.zeebe.exporter.api.context.Controller;
import io.camunda.zeebe.protocol.record.Record;
import io.camunda.zeebe.protocol.record.value.MessageRecordValue;

public class MessageEntityMapper {
    public static void ToMessageEntity(Record<MessageRecordValue> record,
                                       Controller controller,
                                       MessageRepository messageRepository){
        MessageEntity entity =
                messageRepository
                        .findById(record.getKey())
                        .orElseGet(() -> BaseEntityMapper
                                .MapBaseEntityProperties(MessageEntity.builder(), record)
                                .Name(record.getValue().getName())
                                .CorrelationKey(record.getValue().getCorrelationKey())
                                .MessageId(record.getValue().getMessageId())
                                .Payload(record.getValue().getVariables().toString())
                                .build());

        entity.setState(record.getIntent().name().toLowerCase());
        entity.setTimestamp(record.getTimestamp());

        messageRepository.save(entity, entity.getKey_());

        controller.updateLastExportedRecordPosition(record.getPosition());
    }
}
