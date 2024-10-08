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
    private String BpmnProcessId;

    private Integer Version;

    private Long ProcessDefinitionKey;

    private String ElementId;

    private Long FlowScopeKey;

    private String BpmnElementType;

    private Long ParentProcessInstanceKey;

    private Long ParentElementInstanceKey;

    private String BpmnEventType;

    private String State;

    private Long StartTime;

    private Long EndTime;
}
