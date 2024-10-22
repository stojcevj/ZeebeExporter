package com.joci.entites;

import com.joci.entites.BaseEntites.BaseEntityNoKey;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.Length;

@Entity(name = "Error")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class ErrorEntity extends BaseEntityNoKey {
    @Id
    private Long Id;

    private Long ErrorEventPosition;

    private Long ProcessInstanceKey;

    @Column(length = Length.LONG16)
    private String ExceptionMessage;

    private String StackTrace;

    private Long Timestamp;
}
