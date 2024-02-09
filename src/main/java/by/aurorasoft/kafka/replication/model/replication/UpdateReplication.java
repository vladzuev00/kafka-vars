package by.aurorasoft.kafka.replication.model.replication;

import by.aurorasoft.kafka.replication.model.transportable.TransportableDto;
import by.aurorasoft.kafka.replication.model.transportable.TransportableReplication;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;

import static by.aurorasoft.kafka.replication.model.transportable.TransportableReplication.createTransportableUpdate;

public final class UpdateReplication extends ReplicationWithDto {

    public UpdateReplication(final AbstractDto<?> dto) {
        super(dto);
    }

    @Override
    protected TransportableReplication createTransportable(final TransportableDto dto) {
        return createTransportableUpdate(dto);
    }
}
