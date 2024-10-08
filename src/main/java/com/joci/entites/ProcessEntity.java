package com.joci.entites;


import jakarta.persistence.*;
import lombok.Builder;
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

    @Column(nullable = false)
    private Long DeploymentKey;

    @Column(nullable = false)
    private String BpmnProcessId;

    @Column(nullable = false)
    private Integer Version;

    @Lob
    @Column(nullable = false)
    private byte[] Resource;

    private String ResourceName;
}


