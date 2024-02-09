package by.aurorasoft.kafka.replication.model.replication;

import by.aurorasoft.kafka.replication.model.ReplicationProducingContext;
import by.aurorasoft.kafka.replication.model.TransportableDto;
import by.aurorasoft.kafka.replication.model.TransportableReplication;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class ReplicationWithDto implements Replication {
    private final AbstractDto<?> dto;

    @Override
    public final Object getEntityId() {
        return dto.getId();
    }

    @Override
    @SuppressWarnings("unchecked")
    public final <DTO extends AbstractDto<?>> TransportableReplication mapToTransportable(final ReplicationProducingContext<DTO> context) {
        final DTO dto = (DTO) this.dto;
        final TransportableDto transportableDto = context.getDtoToTransportableMapper().apply(dto);
        return createTransportableReplication(transportableDto);
    }

    protected abstract TransportableReplication createTransportableReplication(final TransportableDto dto);
}
