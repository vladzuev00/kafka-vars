package by.aurorasoft.kafka.replication.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
@FieldNameConstants
public abstract class TransportableReplication {
    private final ReplicationOperation operation;
}
