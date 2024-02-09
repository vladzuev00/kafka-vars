package by.aurorasoft.kafka.replication.it.model;

import by.aurorasoft.kafka.replication.model.transportable.TransportableReplication;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@FieldNameConstants
public final class TransportablePersonReplication extends TransportableReplication {
    private final Long entityId;
    private final String name;
    private final String surname;

    public TransportablePersonReplication(final ReplicationOperation operation,
                                          final Long entityId,
                                          final String name,
                                          final String surname) {
        super(operation);
        this.entityId = entityId;
        this.name = name;
        this.surname = surname;
    }
}
