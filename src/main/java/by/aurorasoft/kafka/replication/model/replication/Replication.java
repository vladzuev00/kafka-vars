package by.aurorasoft.kafka.replication.model.replication;

import by.aurorasoft.kafka.replication.model.ReplicationOperation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public abstract class Replication<ENTITY_ID, BODY> {
    private final ReplicationOperation operation;
    private final BODY body;
}
