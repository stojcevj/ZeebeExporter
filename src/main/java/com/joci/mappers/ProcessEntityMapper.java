package com.joci.mappers;

import com.joci.entites.ProcessEntity;
import com.joci.repository.ProcessRepository;
import io.camunda.zeebe.exporter.api.context.Controller;
import io.camunda.zeebe.protocol.Protocol;
import io.camunda.zeebe.protocol.record.Record;
import io.camunda.zeebe.protocol.record.value.DeploymentRecordValue;

import java.util.List;

public class ProcessEntityMapper {
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
                .<ProcessEntity>map(process -> BaseEntityMapper
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
}
