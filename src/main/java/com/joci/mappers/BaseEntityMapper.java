package com.joci.mappers;

import com.joci.entites.BaseEntity;
import io.camunda.zeebe.protocol.record.Record;

public class BaseEntityMapper {
    @SuppressWarnings("unchecked")
    public static <T extends BaseEntity.BaseEntityBuilder<?, ?>> T MapBaseEntityProperties(T builder, Record<?> record) {
        return (T) builder
                .Key_(record.getKey())
                .Position(record.getPosition())
                .SourceRecordPosition(record.getSourceRecordPosition())
                .Timestamp(record.getTimestamp())
                .Intent(record.getIntent().name())
                .PartitionId(record.getPartitionId())
                .BrokerVersion(record.getBrokerVersion());
    }
}