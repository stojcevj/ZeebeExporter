package com.joci.entites;

import com.joci.entites.BaseEntites.BaseEntityNoKey;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity(name = "MessageSubscription")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class MessageSubscriptionEntity extends BaseEntityNoKey {

    @Id
    private String Id;

    private String MessageName;

    private String CorrelationKey;

    private Long ProcessInstanceKey;

    private Long ElementInstanceKey;

    private Long ProcessDefinitionKey;

    private String TargetFlowNodeId;

    private String State;

    private String BpmnProcessId;

    private boolean IsInterrupting;
}
