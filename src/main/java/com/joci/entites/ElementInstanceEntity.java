package com.joci.entites;

import com.joci.entites.BaseEntites.BaseEntityNoKey;
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
public class ElementInstanceEntity extends BaseEntityNoKey {

    @Id
    private String Id;

    private Long Key_;

    private Long ProcessDefinitionKey;

    private Long ProcessInstanceKey;

    private String ElementId;

    private String BpmnElementType;

    private Long FlowScopeKey;

    public final String getGeneratedIdentifier() {
        return this.getPartitionId() + "-" + this.getPosition();
    }

    @PrePersist
    private void prePersistDeriveIdField() {
        setId(getGeneratedIdentifier());
    }
}
