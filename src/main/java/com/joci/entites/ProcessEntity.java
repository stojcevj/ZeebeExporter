package com.joci.entites;


import com.joci.entites.BaseEntites.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity(name = "Process")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class ProcessEntity extends BaseEntity {

    private Long DeploymentKey;

    private String BpmnProcessId;

    private Integer Version;

    @Lob
    private byte[] Resource;

    private String ResourceName;
}


