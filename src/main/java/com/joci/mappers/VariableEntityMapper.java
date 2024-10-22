package com.joci.mappers;

import com.joci.entites.VariableEntity;
import com.joci.repository.VariableRepository;
import io.camunda.zeebe.exporter.api.context.Controller;
import io.camunda.zeebe.protocol.record.Record;
import io.camunda.zeebe.protocol.record.value.VariableRecordValue;

public class VariableEntityMapper {
    public static void ToVariableEntity(Record<VariableRecordValue> record,
                                        Controller controller,
                                        VariableRepository variableRepository) {
        var builder =
                BaseEntityMapper.MapBaseEntityNoKeyProperties(VariableEntity.builder(), record);

        var generatedId = builder.build().getGeneratedIdentifier();

        variableRepository
                .findById(builder.build().getGeneratedIdentifier())
                .orElseGet(() -> {
                    builder
                            .ProcessInstanceKey(record.getValue().getProcessInstanceKey())
                            .ScopeKey(record.getValue().getScopeKey())
                            .ProcessDefinitionKey(record.getValue().getProcessDefinitionKey())
                            .Name(record.getValue().getName())
                            .Value(record.getValue().getValue())
                            .State(record.getIntent().name().toLowerCase())
                            .BpmnProcessId(record.getValue().getBpmnProcessId());

                    var variableEntity = builder.build();
                    variableRepository.save(variableEntity, variableEntity.getGeneratedIdentifier());

                    return variableEntity;
                });

        controller.updateLastExportedRecordPosition(record.getPosition());
    }
}
