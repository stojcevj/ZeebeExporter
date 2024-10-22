package com.joci.mappers;

import com.joci.entites.TimerEntity;
import com.joci.repository.TimerRepository;
import io.camunda.zeebe.exporter.api.context.Controller;
import io.camunda.zeebe.protocol.record.Record;
import io.camunda.zeebe.protocol.record.value.TimerRecordValue;

public class TimerEntityMapper {
    public static void ToTimerEntity(Record<TimerRecordValue> record,
                                     Controller controller,
                                     TimerRepository timerRepository) {
        TimerEntity entity =
                timerRepository
                        .findById(record.getKey())
                        .orElseGet(() -> {
                            var newEntity = BaseEntityMapper
                                    .MapBaseEntityProperties(TimerEntity.builder(), record)
                                    .ProcessDefinitionKey(record.getValue().getProcessDefinitionKey())
                                    .TargetElementId(record.getValue().getTargetElementId())
                                    .DueDate(record.getValue().getDueDate())
                                    .Repetitions(record.getValue().getRepetitions())
                                    .build();

                            if(record.getValue().getProcessInstanceKey() > 0){
                                newEntity.setProcessInstanceKey(record.getValue().getProcessInstanceKey());
                                newEntity.setElementInstanceKey(record.getValue().getElementInstanceKey());
                            }

                            return newEntity;
                        });

        entity.setState(record.getIntent().name().toLowerCase());
        entity.setTimestamp(record.getTimestamp());

        timerRepository.save(entity, entity.getKey_());

        controller.updateLastExportedRecordPosition(record.getPosition());
    }
}
