package com.joci.mappers;

import com.joci.entites.ErrorEntity;
import com.joci.repository.ErrorRepository;
import io.camunda.zeebe.exporter.api.context.Controller;
import io.camunda.zeebe.protocol.record.Record;
import io.camunda.zeebe.protocol.record.value.ErrorRecordValue;

public class ErrorMapper {
    public static void ToErrorEntity(Record<ErrorRecordValue> record,
                                                   Controller controller,
                                                   ErrorRepository errorRepository) {
        var builder = BaseEntityMapper
                .MapBaseEntityNoKeyProperties(ErrorEntity.builder(), record);

        ErrorEntity entity = errorRepository
                .findById(builder.build().getPosition())
                .orElseGet(() -> builder
                        .Id(record.getPosition())
                        .ErrorEventPosition(record.getValue().getErrorEventPosition())
                        .ProcessInstanceKey(record.getValue().getProcessInstanceKey())
                        .ExceptionMessage(record.getValue().getExceptionMessage())
                        .StackTrace(record.getValue().getStacktrace())
                        .build());

        errorRepository.save(entity, entity.getPosition());

        controller.updateLastExportedRecordPosition(record.getPosition());
    }
}
