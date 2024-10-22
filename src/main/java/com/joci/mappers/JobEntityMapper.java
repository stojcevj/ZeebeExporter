package com.joci.mappers;

import com.joci.entites.JobEntity;
import com.joci.repository.JobRepository;
import io.camunda.zeebe.exporter.api.context.Controller;
import io.camunda.zeebe.protocol.record.Record;
import io.camunda.zeebe.protocol.record.value.JobRecordValue;

public class JobEntityMapper {
    public static void ToJobEntity(Record<JobRecordValue> record,
                                   Controller controller,
                                   JobRepository jobRepository){
        JobEntity entity =
                jobRepository
                        .findById(record.getKey())
                        .orElseGet(() -> BaseEntityMapper
                                .MapBaseEntityProperties(JobEntity.builder(), record)
                                .ProcessInstanceKey(record.getValue().getProcessInstanceKey())
                                .ElementInstanceKey(record.getValue().getElementInstanceKey())
                                .JobType(record.getValue().getType())
                                .build());

        entity.setState(record.getIntent().name().toLowerCase());
        entity.setTimestamp(record.getTimestamp());
        entity.setWorker(record.getValue().getWorker());
        entity.setRetries(record.getValue().getRetries());
        jobRepository.save(entity, entity.getKey_());

        controller.updateLastExportedRecordPosition(record.getPosition());
    }
}
