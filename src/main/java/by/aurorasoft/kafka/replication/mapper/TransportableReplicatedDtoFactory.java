package by.aurorasoft.kafka.replication.mapper;

import by.aurorasoft.kafka.replication.model.transportable.TransportableDto;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class TransportableReplicatedDtoFactory<DTO extends AbstractDto<?>> {
    private final Class<DTO> dtoType;

    public final TransportableDto create(final AbstractDto<?> dto) {
        return createInternal(dtoType.cast(dto));
    }

    protected abstract TransportableDto createInternal(final DTO dto);
}
