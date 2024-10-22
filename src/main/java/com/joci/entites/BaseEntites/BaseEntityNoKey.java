package com.joci.entites.BaseEntites;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public abstract class BaseEntityNoKey {

    @Column(nullable = false)
    private Long Position;

    private Long SourceRecordPosition;

    @Column(nullable = false)
    private Long Timestamp;

    @Column(nullable = false)
    private String Intent;

    @Column(nullable = false)
    private Integer PartitionId;

    private String BrokerVersion;
}
