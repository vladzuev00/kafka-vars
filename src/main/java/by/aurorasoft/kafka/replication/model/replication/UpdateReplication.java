package by.aurorasoft.kafka.replication.model.replication;

import by.aurorasoft.kafka.replication.model.TransportableDto;
import by.aurorasoft.kafka.replication.model.TransportableReplication;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;

import static by.aurorasoft.kafka.replication.model.ReplicationType.UPDATE;

public final class UpdateReplication extends ReplicationWithDto {

    public UpdateReplication(final AbstractDto<?> dto) {
        super(dto);
    }

    @Override
    protected TransportableReplication createTransportableReplication(final TransportableDto dto) {
        return TransportableReplication.builder()
                .type(UPDATE)
                .dto(dto)
                .build();
    }
}
