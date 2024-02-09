package by.aurorasoft.kafka.replication.model.replication;

import by.aurorasoft.kafka.replication.model.transportable.TransportableDto;
import by.aurorasoft.kafka.replication.model.transportable.TransportableReplication;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;

import static by.aurorasoft.kafka.replication.model.transportable.TransportableReplication.createTransportableSave;

public final class SaveReplication extends ReplicationWithDto {

    public SaveReplication(final AbstractDto<?> dto) {
        super(dto);
    }

    @Override
    protected TransportableReplication createTransportable(final TransportableDto dto) {
        return createTransportableSave(dto);
    }
}
