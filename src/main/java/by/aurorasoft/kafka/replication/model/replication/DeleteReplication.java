package by.aurorasoft.kafka.replication.model.replication;

import by.aurorasoft.kafka.replication.model.transportable.TransportableDto;
import by.aurorasoft.kafka.replication.model.transportable.TransportableReplication;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

import static by.aurorasoft.kafka.replication.model.transportable.TransportableReplication.createTransportableDelete;

@RequiredArgsConstructor
@Getter
public final class DeleteReplication implements Replication {
    private final Object entityId;

    @Override
    public <DTO extends AbstractDto<?>> TransportableReplication mapToTransportable(final Function<DTO, TransportableDto> dtoMapper) {
        return createTransportableDelete(entityId);
    }
}
