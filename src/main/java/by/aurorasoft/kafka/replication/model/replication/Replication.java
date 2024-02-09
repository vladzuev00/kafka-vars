package by.aurorasoft.kafka.replication.model.replication;

import by.aurorasoft.kafka.replication.model.transportable.TransportableDto;
import by.aurorasoft.kafka.replication.model.transportable.TransportableReplication;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;

import java.util.function.Function;

public interface Replication {
    Object getEntityId();
    <DTO extends AbstractDto<?>> TransportableReplication mapToTransportable(final Function<DTO, TransportableDto> dtoMapper);
}
