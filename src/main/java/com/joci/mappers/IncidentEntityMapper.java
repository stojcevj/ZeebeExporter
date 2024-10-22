package com.joci.mappers;

import com.joci.entites.IncidentEntity;
import com.joci.repository.IncidentRepository;
import io.camunda.zeebe.exporter.api.context.Controller;
import io.camunda.zeebe.protocol.record.Record;
import io.camunda.zeebe.protocol.record.intent.IncidentIntent;
import io.camunda.zeebe.protocol.record.value.IncidentRecordValue;

public class IncidentEntityMapper {
    public static void ToIncidentEntity(Record<IncidentRecordValue> record,
                                        Controller controller,
                                        IncidentRepository incidentRepository){
        IncidentEntity entity =
                incidentRepository
                        .findById(record.getKey())
                        .orElseGet(() -> BaseEntityMapper
                                .MapBaseEntityProperties(IncidentEntity.builder(), record)
                                .BpmnProcessId(record.getValue().getBpmnProcessId())
                                .ProcessDefinitionKey(record.getValue().getProcessDefinitionKey())
                                .ProcessInstanceKey(record.getValue().getProcessInstanceKey())
                                .ElementInstanceKey(record.getValue().getElementInstanceKey())
                                .JobKey(record.getValue().getJobKey())
                                .ErrorType(record.getValue().getErrorType().name())
                                .ErrorMessage(record.getValue().getErrorMessage())
                                .build());

        if (record.getIntent() == IncidentIntent.CREATED) {
            entity.setCreated(record.getTimestamp());
            incidentRepository.save(entity, entity.getKey_());
        } else if (record.getIntent() == IncidentIntent.RESOLVED) {
            entity.setResolved(record.getTimestamp());
            incidentRepository.save(entity, entity.getKey_());
        }
    }
}
