package by.aurorasoft.kafka.replication.model.replication;

import by.aurorasoft.kafka.replication.model.transportable.TransportableDto;
import by.aurorasoft.kafka.replication.model.transportable.TransportableReplication;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

@RequiredArgsConstructor
public abstract class ReplicationWithDto implements Replication {
    private final AbstractDto<?> dto;

    @Override
    public final Object getEntityId() {
        return dto.getId();
    }

    @Override
    @SuppressWarnings("unchecked")
    public final <DTO extends AbstractDto<?>> TransportableReplication mapToTransportable(final Function<DTO, TransportableDto> dtoMapper) {
        final DTO dto = (DTO) this.dto;
        final TransportableDto transportableDto = dtoMapper.apply(dto);
        return createTransportable(transportableDto);
    }

    protected abstract TransportableReplication createTransportable(final TransportableDto dto);
}
