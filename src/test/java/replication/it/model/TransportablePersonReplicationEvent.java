package replication.it.model;

import by.aurorasoft.kafka.replication.model.ReplicationOperation;
import by.aurorasoft.kafka.replication.model.TransportableReplication;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class TransportablePersonReplicationEvent extends TransportableReplication<Long> {
    private final String name;
    private final String surname;

    public TransportablePersonReplicationEvent(final ReplicationOperation operation,
                                               final Long entityId,
                                               final String name,
                                               final String surname) {
        super(operation, entityId);
        this.name = name;
        this.surname = surname;
    }
}
