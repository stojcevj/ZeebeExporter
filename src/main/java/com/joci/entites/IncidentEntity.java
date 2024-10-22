package com.joci.entites;

import com.joci.entites.BaseEntites.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.Length;

@Entity(name = "Incident")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class IncidentEntity extends BaseEntity {
    private String BpmnProcessId;

    private Long ProcessDefinitionKey;

    private Long ProcessInstanceKey;

    private Long ElementInstanceKey;

    private Long JobKey;

    @Column(length = Length.LONG16)
    private String ErrorMessage;

    private String ErrorType;

    private Long Created;

    private Long Resolved;
}
