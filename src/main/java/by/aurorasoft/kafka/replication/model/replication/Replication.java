package by.aurorasoft.kafka.replication.model.replication;

import by.aurorasoft.kafka.replication.model.TransportableReplication;
import by.aurorasoft.kafka.replication.producer.ReplicationProducingContext;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import by.nhorushko.crudgeneric.v2.service.AbsServiceCRUD;

public interface Replication<ID, DTO extends AbstractDto<ID>> {
    ID getEntityId();

    TransportableReplication createTransportable(final ReplicationProducingContext<ID, DTO> context);

    void execute(final AbsServiceCRUD<ID, ?, DTO, ?> service);
}
