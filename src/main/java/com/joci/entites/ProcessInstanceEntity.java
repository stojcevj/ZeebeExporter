package com.joci.entites;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity(name = "ProcessInstance")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class ProcessInstanceEntity extends BaseEntity{
    String BpmnProcessId;

    Integer Version;

    Long ProcessDefinitionKey;

    String ElementId;

    Long FlowScopeKey;

    String BpmnElementType;

    Long ParentProcessInstanceKey;

    Long ParentElementInstanceKey;

    String BpmnEventType;

    String State;

    Long StartTime;

    Long EndTime;
}
