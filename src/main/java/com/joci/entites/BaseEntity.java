package com.joci.entites;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public abstract class BaseEntity {

    @Id
    @Column(nullable = false, unique = true)
    private Long Key_;

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