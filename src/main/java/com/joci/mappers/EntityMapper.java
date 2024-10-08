package com.joci.mappers;

import com.joci.entites.ElementInstanceEntity;
import com.joci.entites.ProcessEntity;
import com.joci.entites.ProcessInstanceEntity;
import com.joci.entites.VariableEntity;
import com.joci.repository.impl.ElementInstanceRepository;
import com.joci.repository.impl.ProcessInstanceRepository;
import com.joci.repository.impl.ProcessRepository;
import com.joci.repository.impl.VariableRepository;
import io.camunda.zeebe.exporter.api.context.Controller;
import io.camunda.zeebe.protocol.Protocol;
import io.camunda.zeebe.protocol.record.intent.ProcessInstanceIntent;
import io.camunda.zeebe.protocol.record.value.DeploymentRecordValue;
import io.camunda.zeebe.protocol.record.Record;
import io.camunda.zeebe.protocol.record.value.ProcessInstanceRecordValue;
import io.camunda.zeebe.protocol.record.value.VariableRecordValue;
import io.camunda.zeebe.protocol.record.value.deployment.DeploymentResource;
import io.camunda.zeebe.protocol.record.value.deployment.ProcessMetadataValue;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
@SuppressWarnings("unchecked")
public class EntityMapper {
    public static void ToProcessEntity(Record<DeploymentRecordValue> record,
                                       Controller controller,
                                       ProcessRepository processRepository) {
        if (record.getPartitionId() != Protocol.DEPLOYMENT_PARTITION){
            return;
        }

        var entities = (List<ProcessEntity>) record
                .getValue()
                .getProcessesMetadata()
                .stream()
                .map(process -> BaseEntityMapper
                        .MapBaseEntityProperties(ProcessEntity.builder(), record)
                        .BpmnProcessId(process.getBpmnProcessId())
                        .Resource(process.getChecksum())
                        .Version(process.getVersion())
                        .ResourceName(process.getResourceName())
                        .DeploymentKey(record.getKey())
                        .Key_(process.getProcessDefinitionKey())
                        .build()
                )
                .toList();

        for (ProcessEntity processEntity : entities)
            processRepository.save(processEntity, processEntity.getKey_());

        controller.updateLastExportedRecordPosition(record.getPosition());
    }

    public static void ToProcessAndElementInstanceEntity(Record<ProcessInstanceRecordValue> record,
                                                         Controller controller,
                                                         ProcessInstanceRepository processInstanceRepository,
                                                         ElementInstanceRepository elementInstanceRepository) {
        if(record.getValue().getProcessInstanceKey() == record.getKey()) {
            ProcessInstanceEntity entity =
                    processInstanceRepository
                            .findById(record.getValue().getProcessInstanceKey());

            if (entity == null)
                entity = BaseEntityMapper
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
                        .build();

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

        ElementInstanceEntity elementInstanceEntity =
                ElementInstanceEntity.builder()
                        .PartitionId(record.getPartitionId())
                        .Position(record.getPosition())
                        .build();

        if(elementInstanceRepository.findById(elementInstanceEntity.getGeneratedIdentifier()) == null){
            elementInstanceEntity.setKey_(record.getKey());
            elementInstanceEntity.setIntent(record.getIntent().name());
            elementInstanceEntity.setTimestamp(record.getTimestamp());
            elementInstanceEntity.setProcessInstanceKey(record.getValue().getProcessInstanceKey());
            elementInstanceEntity.setElementId(record.getValue().getElementId());
            elementInstanceEntity.setFlowScopeKey(record.getValue().getFlowScopeKey());
            elementInstanceEntity.setProcessDefinitionKey(record.getValue().getProcessDefinitionKey());
            elementInstanceEntity.setBpmnElementType(record.getValue().getBpmnElementType().name());
            elementInstanceRepository.save(elementInstanceEntity, elementInstanceEntity.getGeneratedIdentifier());
        }

        controller.updateLastExportedRecordPosition(record.getPosition());
    }

    public static void ToVariableEntity(Record<VariableRecordValue> record,
                                       Controller controller,
                                       VariableRepository variableRepository) {
        VariableEntity variableEntity =
                VariableEntity.builder()
                        .PartitionId(record.getPartitionId())
                        .Position(record.getPosition())
                        .build();

        if(variableRepository.findById(variableEntity.getGeneratedIdentifier()) == null){
            variableEntity.setIntent(record.getIntent().name());
            variableEntity.setTimestamp(record.getTimestamp());
            variableEntity.setProcessInstanceKey(record.getValue().getProcessInstanceKey());
            variableEntity.setScopeKey(record.getValue().getScopeKey());
            variableEntity.setProcessDefinitionKey(record.getValue().getProcessDefinitionKey());
            variableEntity.setName(record.getValue().getName());
            variableEntity.setBrokerVersion(record.getBrokerVersion());
            variableEntity.setSourceRecordPosition(record.getSourceRecordPosition());
            variableEntity.setValue(record.getValue().getValue());
            variableEntity.setState(record.getIntent().name().toLowerCase());
            variableEntity.setBpmnProcessId(record.getValue().getBpmnProcessId());
            variableRepository.save(variableEntity, variableEntity.getGeneratedIdentifier());
        }

        controller.updateLastExportedRecordPosition(record.getPosition());
    }
}

