package com.joci.entites;

import com.joci.entites.BaseEntites.BaseEntityNoKey;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.Length;

@Entity(name = "Variable")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class VariableEntity extends BaseEntityNoKey {

    @Id
    private String Id;

    private Long ProcessInstanceKey;

    private Long ProcessDefinitionKey;

    private String Name;

    @Column(length = Length.LONG32)
    private String Value;

    private Long ScopeKey;

    private String BpmnProcessId;

    private String State;

    public final String getGeneratedIdentifier() {
        return this.getPartitionId() + "-" + this.getPosition();
    }

    @PrePersist
    private void prePersistDeriveIdField(){
        setId(getGeneratedIdentifier());
    }
}
