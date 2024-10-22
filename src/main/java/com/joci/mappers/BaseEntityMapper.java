package com.joci.mappers;

import com.joci.entites.BaseEntites.BaseEntity;
import com.joci.entites.BaseEntites.BaseEntityNoKey;
import io.camunda.zeebe.protocol.record.Record;
@SuppressWarnings("unchecked")
public class BaseEntityMapper {
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

    public static <T extends BaseEntityNoKey.BaseEntityNoKeyBuilder<?, ?>> T MapBaseEntityNoKeyProperties(T builder, Record<?> record) {
        return (T) builder
                .Position(record.getPosition())
                .SourceRecordPosition(record.getSourceRecordPosition())
                .Timestamp(record.getTimestamp())
                .Intent(record.getIntent().name())
                .PartitionId(record.getPartitionId())
                .BrokerVersion(record.getBrokerVersion());
    }
}