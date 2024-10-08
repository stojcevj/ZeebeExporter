package com.joci.entites;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity(name = "ElementInstance")
/*@Table(indexes = {
        @Index(name = "ElementInstance_ProcessInstanceKeyIndex", columnList = "ProcessInstanceKey"),
        @Index(name = "ElementInstance_ProcessDefinitionKeyIndex", columnList = "ProcessDefinitionKey"),
        @Index(name = "ElementInstance_IntentIndex", columnList = "Intent"),
        @Index(name = "ElementInstance_BpmnElementTypeIndex", columnList = "BpmnElementType"),
})*/
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class ElementInstanceEntity{
    @Id
    private String Id;

    private Long Position;

    private Integer PartitionId;

    private Long Key_;

    private String Intent;

    private Long ProcessInstanceKey;

    private String ElementId;

    private String BpmnElementType;

    private Long FlowScopeKey;

    private Long ProcessDefinitionKey;

    private Long Timestamp;

    private void setId(final String Id) {
        this.Id = Id;
    }

    public final String getGeneratedIdentifier() {
        return this.PartitionId + "-" + this.Position;
    }

    @PrePersist
    private void prePersistDeriveIdField() {
        setId(getGeneratedIdentifier());
    }
}
