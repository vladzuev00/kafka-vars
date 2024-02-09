package by.aurorasoft.kafka.replication.model.replication;

import by.aurorasoft.kafka.replication.model.ReplicationProducingContext;
import by.aurorasoft.kafka.replication.model.TransportableReplication;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;

public interface Replication {
    Object getEntityId();
    <DTO extends AbstractDto<?>> TransportableReplication mapToTransportable(final ReplicationProducingContext<DTO> context);
}
