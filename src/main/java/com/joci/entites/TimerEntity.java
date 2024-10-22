package com.joci.entites;

import com.joci.entites.BaseEntites.BaseEntity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity(name = "Timer")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class TimerEntity extends BaseEntity {
    private Long ProcessDefinitionKey;

    private Long ProcessInstanceKey;

    private Long ElementInstanceKey;

    private String TargetElementId;

    private Long DueDate;

    private String State;

    private Integer Repetitions;
}
