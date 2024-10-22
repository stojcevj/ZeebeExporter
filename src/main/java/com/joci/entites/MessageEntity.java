package com.joci.entites;

import com.joci.entites.BaseEntites.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.Length;

@Entity(name = "Message")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class MessageEntity extends BaseEntity {
    private String Name;

    private String CorrelationKey;

    private String MessageId;

    @Column(length = Length.LONG32)
    private String Payload;

    private String State;
}
