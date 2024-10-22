package com.joci.entites;

import com.joci.entites.BaseEntites.BaseEntity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity(name = "Job")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class JobEntity extends BaseEntity {
    private Long ProcessInstanceKey;

    private Long ElementInstanceKey;

    private String JobType;

    private String Worker;

    private String State;

    private Integer Retries;
}
