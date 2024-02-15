package by.aurorasoft.kafka.replication.model.replication;

import by.aurorasoft.kafka.replication.model.TransportableReplication;
import by.aurorasoft.kafka.replication.model.TransportableReplication.ReplicationType;
import by.aurorasoft.kafka.replication.producer.ReplicatedDtoSerializer;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import by.nhorushko.crudgeneric.v2.service.AbsServiceCRUD;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EqualsAndHashCode
public abstract class Replication<ID, DTO extends AbstractDto<ID>> {
    private final DTO dto;

    public final ID getEntityId() {
        return dto.getId();
    }

    public final TransportableReplication createTransportable(final ReplicatedDtoSerializer<ID, DTO> serializer) {
        final ReplicationType type = getType();
        final String dtoJson = serializer.serialize(dto);
        return new TransportableReplication(type, dtoJson);
    }

    public final void execute(final AbsServiceCRUD<ID, ?, DTO, ?> service) {
        execute(service, dto);
    }

    protected abstract void execute(final AbsServiceCRUD<ID, ?, DTO, ?> service, final DTO dto);

    protected abstract ReplicationType getType();
}
