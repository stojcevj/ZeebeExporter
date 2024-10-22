package com.joci.mappers;

import com.joci.entites.ElementInstanceEntity;
import com.joci.entites.ProcessInstanceEntity;
import com.joci.repository.*;
import io.camunda.zeebe.exporter.api.context.Controller;
import io.camunda.zeebe.protocol.record.Record;
import io.camunda.zeebe.protocol.record.intent.ProcessInstanceIntent;
import io.camunda.zeebe.protocol.record.value.ProcessInstanceRecordValue;

public class ProcessAndElementInstanceEntityMapper {
    public static void ToProcessAndElementInstanceEntity(Record<ProcessInstanceRecordValue> record,
                                                         Controller controller,
                                                         ProcessInstanceRepository processInstanceRepository,
                                                         ElementInstanceRepository elementInstanceRepository) {
        if(record.getValue().getProcessInstanceKey() == record.getKey()) {
            ProcessInstanceEntity entity =
                    processInstanceRepository
                            .findById(record.getValue().getProcessInstanceKey())
                            .orElseGet(() -> BaseEntityMapper
                                    .MapBaseEntityProperties(ProcessInstanceEntity.builder(), record)
                                    .BpmnProcessId(record.getValue().getBpmnProcessId())
                                    .Version(record.getValue().getVersion())
                                    .ProcessDefinitionKey(record.getValue().getProcessDefinitionKey())
                                    .ElementId(record.getValue().getElementId())
                                    .FlowScopeKey(record.getValue().getFlowScopeKey())
                                    .BpmnElementType(record.getValue().getBpmnElementType().name())
                                    .ParentProcessInstanceKey(record.getValue().getParentProcessInstanceKey())
                                    .ParentElementInstanceKey(record.getValue().getParentElementInstanceKey())
                                    .BpmnEventType(record.getValue().getBpmnEventType().name())
                                    .Key_(record.getValue().getProcessInstanceKey())
                                    .build());

            if (record.getIntent() == ProcessInstanceIntent.ELEMENT_ACTIVATED) {
                entity.setState("Active");
                entity.setStartTime(record.getTimestamp());
                processInstanceRepository.save(entity, entity.getKey_());
            } else if (record.getIntent() == ProcessInstanceIntent.ELEMENT_COMPLETED) {
                entity.setState("Completed");
                entity.setEndTime(record.getTimestamp());
                processInstanceRepository.save(entity, entity.getKey_());
            } else if (record.getIntent() == ProcessInstanceIntent.ELEMENT_TERMINATED) {
                entity.setState("Terminated");
                entity.setEndTime(record.getTimestamp());
                processInstanceRepository.save(entity, entity.getKey_());
            }
        }

        var builder =
                BaseEntityMapper.MapBaseEntityNoKeyProperties(ElementInstanceEntity.builder(), record);

        elementInstanceRepository
                .findById(builder.build().getGeneratedIdentifier())
                .orElseGet(() -> {
                    builder
                            .Key_(record.getKey())
                            .ProcessInstanceKey(record.getValue().getProcessInstanceKey())
                            .ElementId(record.getValue().getElementId())
                            .FlowScopeKey(record.getValue().getFlowScopeKey())
                            .ProcessDefinitionKey(record.getValue().getProcessDefinitionKey())
                            .BpmnElementType(record.getValue().getBpmnElementType().name());

                    var elementInstanceEntity = builder.build();
                    elementInstanceRepository.save(elementInstanceEntity, elementInstanceEntity.getGeneratedIdentifier());
                    return elementInstanceEntity;
                });

        controller.updateLastExportedRecordPosition(record.getPosition());
    }
}
