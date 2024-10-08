package com.joci.entites;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity(name = "Variable")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class VariableEntity {
    @Id
    private String Id;

    private Long Position;

    private Integer PartitionId;

    private String Name;

    private String Value;

    private Long ScopeKey;

    private Long ProcessInstanceKey;

    private Long ProcessDefinitionKey;

    private String BpmnProcessId;

    private Long Timestamp;

    private String Intent;

    private String State;

    private String BrokerVersion;

    private Long SourceRecordPosition;

    private void setId(final String Id) {
        this.Id = Id;
    }

    public final String getGeneratedIdentifier() {
        return this.PartitionId + "-" + this.Position;
    }

    @PrePersist
    private void prePersistDeriveIdField(){
        setId(getGeneratedIdentifier());
    }
}
