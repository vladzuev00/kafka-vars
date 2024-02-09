package by.aurorasoft.kafka.replication.model.replication;

import by.aurorasoft.kafka.replication.model.TransportableDto;
import by.aurorasoft.kafka.replication.model.TransportableReplication;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;

import static by.aurorasoft.kafka.replication.model.ReplicationType.SAVE;

public final class SaveReplication extends ReplicationWithDto {

    public SaveReplication(final AbstractDto<?> dto) {
        super(dto);
    }

    @Override
    protected TransportableReplication createTransportableReplication(final TransportableDto dto) {
        return TransportableReplication.builder()
                .type(SAVE)
                .dto(dto)
                .build();
    }
}
